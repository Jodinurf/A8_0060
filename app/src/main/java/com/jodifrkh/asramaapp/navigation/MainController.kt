package com.jodifrkh.asramaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jodifrkh.asramaapp.ui.view.bangunan.HomeBgnScreen
import com.jodifrkh.asramaapp.ui.view.mahasiswa.HomeMhsScreen

@Composable
fun MainControllerPage(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeBgn.route
    ) {
        composable(DestinasiHomeMhs.route) {
            HomeMhsScreen(
                navigateToItemEntry = {},
                onDetailClick = {}
            )
        }

        composable(DestinasiHomeBgn.route) {
            HomeBgnScreen(
                navigateToItemEntry = {},
                onDetailClick = {}
            )
        }
    }
}