package com.dikascode.eventbuildingchallenge.network

import com.dikascode.eventbuildingchallenge.model.Category
import com.dikascode.eventbuildingchallenge.model.Item
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("categories.json")
    suspend fun getCategories(): List<Category>

    @GET("categories/{categoryId}.json")
    suspend fun getItemsForCategory(@Path("categoryId") categoryId: String): List<Item>
}