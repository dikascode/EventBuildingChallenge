package com.dikascode.eventbuildingchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dikascode.eventbuildingchallenge.ui.screen.EventCategoriesScreen
import com.dikascode.eventbuildingchallenge.network.RetrofitBuilder
import com.dikascode.eventbuildingchallenge.repository.EventRepository
import com.dikascode.eventbuildingchallenge.viewmodel.EventViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitBuilder.apiService
        val viewModel = ViewModelProvider(this, ViewModelFactory(EventRepository(apiService)))
            .get(EventViewModel::class.java)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "eventScreen") {
                composable("eventScreen") {
                    EventCategoriesScreen(viewModel = viewModel, navController = navController) { categoryId ->
                        navController.navigate("categoryItems/$categoryId")
                    }
                }
            }

        }
    }
}