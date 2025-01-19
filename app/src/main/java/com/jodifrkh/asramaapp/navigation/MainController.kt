package com.jodifrkh.asramaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jodifrkh.asramaapp.ui.view.bangunan.HomeBgnScreen
import com.jodifrkh.asramaapp.ui.view.kamar.HomeKmrScreen
import com.jodifrkh.asramaapp.ui.view.mahasiswa.HomeMhsScreen
import com.jodifrkh.asramaapp.ui.view.pembayaranSewa.HomePsScreen

@Composable
fun MainControllerPage(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomePS.route
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

        composable(DestinasiHomeKmr.route) {
            HomeKmrScreen(
                navigateToItemEntry = {},
                onDetailClick = {},
            )
        }

        composable(DestinasiHomePS.route) {
            HomePsScreen(
                navigateToItemEntry = {},
                onDetailClick = {}
            )
        }
    }
}