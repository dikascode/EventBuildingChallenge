package com.dikascode.eventbuildingchallenge.model

data class Category(
    val id: Int, // Changed from String to Int to match the JSON type
    val title: String,
    val image: String,
    var items: List<Item> = listOf() // Initialized to an empty list
)

