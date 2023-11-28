package com.dikascode.eventbuildingchallenge.repository

import com.dikascode.eventbuildingchallenge.network.ApiService
import com.dikascode.eventbuildingchallenge.model.Category
import com.dikascode.eventbuildingchallenge.model.Item

class EventRepository(private val apiService: ApiService) {
    suspend fun getCategories(): List<Category> = apiService.getCategories()

    suspend fun getItemsForCategory(categoryId: Int): List<Item> {
        return apiService.getItemsForCategory(categoryId.toString())
    }
}