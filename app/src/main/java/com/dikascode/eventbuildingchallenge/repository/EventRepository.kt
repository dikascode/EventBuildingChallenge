package com.dikascode.eventbuildingchallenge.repository


import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.dikascode.eventbuildingchallenge.network.ApiService
import com.dikascode.eventbuildingchallenge.model.Category
import com.dikascode.eventbuildingchallenge.model.Item
import com.dikascode.eventbuildingchallenge.util.Constants.CATEGORY_CONST
import com.dikascode.eventbuildingchallenge.util.Constants.ENCRYPTED_PREF
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EventRepository(private val apiService: ApiService, context: Context) {
    // Initialize MasterKey for secure SharedPreferences encryption
    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    // Create EncryptedSharedPreferences for secure data storage
    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        ENCRYPTED_PREF,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val itemsCache = mutableMapOf<Int, List<Item>>()


    suspend fun getCategories(): List<Category> {
        // Check cache first
        val cachedCategories = getCachedCategories()
        if (cachedCategories.isNotEmpty()) {
            return cachedCategories
        }

        // If cache is empty, fetch from network and update cache
        return try {
            val categories = apiService.getCategories()
            cacheCategories(categories)
            categories
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun cacheCategories(categories: List<Category>) {
        val categoriesJson = Gson().toJson(categories)
        sharedPreferences.edit().putString(CATEGORY_CONST, categoriesJson).apply()
    }

    private fun getCachedCategories(): List<Category> {
        val categoriesJson = sharedPreferences.getString(CATEGORY_CONST, null)
        return if (!categoriesJson.isNullOrEmpty()) {
            val type = object : TypeToken<List<Category>>() {}.type
            Gson().fromJson(categoriesJson, type)
        } else {
            emptyList()
        }
    }

    suspend fun getItemsForCategory(categoryId: Int): List<Item> {
        // Check if items for this category are already cached
        val cachedItems = itemsCache[categoryId]
        if (cachedItems != null) {
            return cachedItems
        }

        // If not cached, fetch from network
        return try {
            val items = apiService.getItemsForCategory(categoryId.toString())
            // Cache the items
            itemsCache[categoryId] = items
            items
        } catch (e: Exception) {
            emptyList()
        }
    }
}