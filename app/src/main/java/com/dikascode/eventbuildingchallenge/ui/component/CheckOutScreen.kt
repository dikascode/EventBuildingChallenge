package com.dikascode.eventbuildingchallenge.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dikascode.eventbuildingchallenge.util.EventUtil.formatBudget
import com.dikascode.eventbuildingchallenge.viewmodel.EventViewModel

@Composable
fun CheckOutScreen(viewModel: EventViewModel) {
    val budget by viewModel.selectedBudget.observeAsState(0 to 0)

    val formattedBudget = formatBudget(Pair(budget.first, budget.second))

    CommonScaffold(
        title = "",
        subtitle = "",
        placeholderText = "",
        topMargin = 0.dp,
        bottomBarContent = { },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircleOverlayContent(
                    title = "Event Saved!",
                    subtitle = formattedBudget,
                )
            }
        }
    )
}

@Composable
fun CircleOverlayContent(
    title: String,
    subtitle: String,
) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .background(
                color = Color.White.copy(alpha = 0.9f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 28.sp,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 40.sp, // Increase font size
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            // Star icon
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(80.dp).padding(vertical = 10.dp)
            )
        }
    }
}

