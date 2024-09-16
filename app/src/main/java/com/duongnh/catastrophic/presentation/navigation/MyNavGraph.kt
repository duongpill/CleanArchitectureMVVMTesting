package com.duongnh.catastrophic.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duongnh.catastrophic.presentation.screen.cat.CatGalleryRoute

@Composable
fun MyNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MyNavDestination.CAT_LIST_ROUTE
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = MyNavDestination.CAT_LIST_ROUTE) {
            CatGalleryRoute()
        }
    }
}