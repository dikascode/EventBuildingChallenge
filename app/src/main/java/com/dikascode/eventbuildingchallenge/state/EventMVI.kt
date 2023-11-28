package com.dikascode.eventbuildingchallenge.state

import com.dikascode.eventbuildingchallenge.model.Category
import com.dikascode.eventbuildingchallenge.model.Item

sealed class EventIntent {
    object LoadData : EventIntent()
    data class AddItemToUserList(val item: Item) : EventIntent()
    data class FetchItems(val categoryId: Int) : EventIntent()
}

data class EventState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val categoryItems: List<Item> = emptyList(), // Added to track items added by the user
    val error: String = ""
)
