package com.dikascode.eventbuildingchallenge.util

import android.content.Context
import android.content.SharedPreferences
import com.dikascode.eventbuildingchallenge.util.Constants.CATEGORY_ID
import com.dikascode.eventbuildingchallenge.util.Constants.CATEGORY_TITLE

object SharedPrefHelper {
    private const val PREF_NAME = "EventBuilderPrefs"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveCategoryId(categoryId: Int) {
        sharedPreferences.edit().putInt(CATEGORY_ID, categoryId).apply()
    }

    fun saveCategoryTitle(categoryTitle: String) {
        sharedPreferences.edit().putString(CATEGORY_TITLE, categoryTitle).apply()
    }

    fun getCategoryTitle(): String {
        return sharedPreferences.getString(CATEGORY_TITLE, "") ?: ""
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }
}
