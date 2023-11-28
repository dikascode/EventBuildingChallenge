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
import com.dikascode.eventbuildingchallenge.ui.component.CheckOutScreen
import com.dikascode.eventbuildingchallenge.ui.screen.CategoryItemsScreen
import com.dikascode.eventbuildingchallenge.util.Constants.CATEGORY_ID
import com.dikascode.eventbuildingchallenge.util.Constants.CHECK_OUT_SCREEN
import com.dikascode.eventbuildingchallenge.util.Constants.EVENT_SCREEN
import com.dikascode.eventbuildingchallenge.util.SharedPrefHelper
import com.dikascode.eventbuildingchallenge.viewmodel.EventViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitBuilder.apiService
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory(EventRepository(apiService, this))
        )[EventViewModel::class.java]

        SharedPrefHelper.init(this)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = EVENT_SCREEN) {
                composable(EVENT_SCREEN) {
                    EventCategoriesScreen(
                        viewModel = viewModel,
                        navController = navController
                    ) { categoryId ->
                        navController.navigate("categoryItems/$categoryId")
                    }
                }
                composable("categoryItems/{categoryId}") { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getString(CATEGORY_ID)!!.toInt()
                    CategoryItemsScreen(categoryId = categoryId, viewModel = viewModel)
                }

                composable(CHECK_OUT_SCREEN) {
                    CheckOutScreen(viewModel = viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPrefHelper.clearData()
    }
}