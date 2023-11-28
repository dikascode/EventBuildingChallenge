package com.dikascode.eventbuildingchallenge.util

fun formatBudget(budget: Pair<Int, Int>): String {
    val formattedMin = "$%,d".format(budget.first)
    val formattedMax = "$%,d".format(budget.second)
    return "$formattedMin - $formattedMax"
}