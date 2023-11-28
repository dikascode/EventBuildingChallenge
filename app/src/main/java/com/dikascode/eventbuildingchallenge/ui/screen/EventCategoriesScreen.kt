package com.dikascode.eventbuildingchallenge.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.dikascode.eventbuildingchallenge.R
import com.dikascode.eventbuildingchallenge.ui.component.CommonScaffold
import com.dikascode.eventbuildingchallenge.state.EventIntent
import com.dikascode.eventbuildingchallenge.state.EventState
import com.dikascode.eventbuildingchallenge.viewmodel.EventViewModel
import com.dikascode.eventbuildingchallenge.ui.component.UnifiedItemCard
import com.dikascode.eventbuildingchallenge.model.Category
import com.dikascode.eventbuildingchallenge.util.Constants.CHECK_OUT_SCREEN


@Composable
fun EventCategoriesScreen(
    viewModel: EventViewModel,
    navController: NavController,
    navigateToCategoryItems: (Int) -> Unit
) {
    val state by viewModel.state.observeAsState(EventState())

    LaunchedEffect(key1 = state.categories.isEmpty()) {
        if (state.categories.isEmpty()) {
            viewModel.processIntents(EventIntent.LoadData)
        }
    }

    CommonScaffold(
        title = stringResource(R.string.event_builder),
        subtitle = stringResource(R.string.add_to_your_event_to_view_our_cost_estimate),
        placeholderText = "-",
        topMargin = 50.dp,
        bottomBarContent = { // Provide the bottom bar content
            BottomAppBar(
                containerColor = Color.White
            ) {
                Button(
                    onClick = { navController.navigate(CHECK_OUT_SCREEN) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5DA3A9))
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        content = { paddingValues ->
            if (state.isLoading) {
                LoadingScreen()
            } else if (state.error.isNotEmpty()) {
                Text(text = "Error: ${state.error}")
            } else {
                CategoryList(
                    categories = state.categories,
                    onCategorySelected = navigateToCategoryItems,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        })
}


@Composable
fun CategoryList(
    categories: List<Category>,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(categories) { category ->
            val isAddedState = remember { mutableStateOf(false) }
            UnifiedItemCard(
                title = category.title,
                imageUrl = category.image,
                onItemSelect = { onCategorySelected(category.id) },
                isCategory = true,
                arrowBesideTitle = true,
                isAddedState = isAddedState
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Dialog(onDismissRequest = {}) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
    }
}
