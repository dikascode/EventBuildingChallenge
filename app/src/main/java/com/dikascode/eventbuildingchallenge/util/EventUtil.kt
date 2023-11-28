package com.dikascode.eventbuildingchallenge.util

import android.content.Context
import android.health.connect.datatypes.units.Length
import android.widget.Toast

object EventUtil {
    fun formatBudget(budget: Pair<Int, Int>): String {
        val formattedMin = "$%,d".format(budget.first)
        val formattedMax = "$%,d".format(budget.second)
        return "$formattedMin - $formattedMax"
    }

    fun showToast(context: Context, message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, length).show()
    }
}