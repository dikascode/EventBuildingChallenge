package com.dikascode.eventbuildingchallenge.model

data class Item(
    val id: Int,
    val title: String,
    val minBudget: Int,
    val maxBudget: Int,
    val avgBudget: Int,
    val image: String,
    var isAdded: Boolean = false
)

