package com.dikascode.eventbuildingchallenge.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dikascode.eventbuildingchallenge.R
import com.dikascode.eventbuildingchallenge.util.formatBudget

@Composable
fun UnifiedItemCard(
    title: String,
    imageUrl: String,
    minBudget: Int? = null,
    maxBudget: Int? = null,
    onItemSelect: () -> Unit,
    isCategory: Boolean = true,
    cardHeight: Dp = 149.dp,
    arrowBesideTitle: Boolean = false,
    isAddedState: MutableState<Boolean>, // Accept mutable state
    onAddItem: (() -> Unit)? = null,
    onRemoveItem: (() -> Unit)? = null
) {
    val backgroundColor = Color(0xFFFFFFFF) // #FFFFFF
    val borderColor = Color(0xFFDFDACC) // #DFDACC
    val arrowColor = Color(0xFF5DA3A9) // #5DA3A9

    // Use the state for isAdded
    val isAdded = isAddedState.value

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 164.dp, height = cardHeight)
            .clip(RoundedCornerShape(5.dp))
            .clickable(onClick = onItemSelect)
            .border(1.dp, borderColor, RoundedCornerShape(5.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box {
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_launcher_background)
                    }
                ),
                contentDescription = title,
                modifier = Modifier
                    .height(104.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            if (!isCategory) {
                IconButton(
                    onClick = {
                        if (!isAdded) {
                            onAddItem?.invoke()
                            isAddedState.value = true
                        } else {
                            onRemoveItem?.invoke()
                        }
                    },
                    enabled = !isAdded,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(32.dp)
                        .padding(top = 8.dp, end = 8.dp)
                        .background(Color.Black.copy(alpha = 0.3f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = if (isAdded) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = if (isAdded) stringResource(R.string.remove_item) else stringResource(
                            R.string.add_item
                        ),
                        tint = Color.White
                    )
                }
            }
        }
        if (!isCategory) {
            val formattedBudget = formatBudget(Pair(minBudget ?:0 , maxBudget ?: 0))
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = formattedBudget,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
            }
        }
        if (isCategory && arrowBesideTitle) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.go_to_category),
                    modifier = Modifier.size(24.dp),
                    tint = arrowColor
                )
            }
        }
    }
}





