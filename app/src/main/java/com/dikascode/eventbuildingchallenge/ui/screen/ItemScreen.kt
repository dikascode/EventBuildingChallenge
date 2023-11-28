package com.dikascode.eventbuildingchallenge.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dikascode.eventbuildingchallenge.model.Item
import com.dikascode.eventbuildingchallenge.state.EventState
import com.dikascode.eventbuildingchallenge.ui.component.CommonScaffold
import com.dikascode.eventbuildingchallenge.ui.component.UnifiedItemCard
import com.dikascode.eventbuildingchallenge.viewmodel.EventViewModel

@Composable
fun CategoryItemsScreen(
    categoryId: Int,
    viewModel: EventViewModel,
) {
    // Fetch items when the screen is first composed or when the categoryId changes
    LaunchedEffect(categoryId) {
        viewModel.fetchItemsForCategory(categoryId)
    }

    val state by viewModel.state.observeAsState(EventState())
    val isLoadingItems by viewModel.isLoadingItems.observeAsState(false)

    CommonScaffold(
        title = "Event Builder",
        subtitle = "Add to your event to view our cost estimate.",
        placeholderText = "-",
        topMargin = 50.dp,
        content = { paddingValues ->
            if (isLoadingItems) {
                // Show loading indicator
                LoadingScreen()
            } else {
                CategoryItemsList(
                    items = state.categoryItems,
                    modifier = Modifier.padding(paddingValues),
                    viewModel
                )
            }
        })
}

@Composable
fun CategoryItemsList(
    items: List<Item>,
    modifier: Modifier = Modifier,
    viewModel: EventViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(items) { item ->
            // Create a MutableState for each item
            val isAddedState = remember { mutableStateOf(item.isAdded) }

            UnifiedItemCard(
                title = item.title,
                imageUrl = item.image,
                onItemSelect = { },
                isCategory = false,
                isAddedState = isAddedState,
                onAddItem = { viewModel.addItemToList(item) },
                minBudget = item.minBudget,
                maxBudget = item.maxBudget,
                cardHeight = 166.dp,
            )
        }
    }
}

