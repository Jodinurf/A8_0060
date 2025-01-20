package com.jodifrkh.asramaapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jodifrkh.asramaapp.ui.view.bangunan.DetailBgnScreen
import com.jodifrkh.asramaapp.ui.view.bangunan.HomeBgnScreen
import com.jodifrkh.asramaapp.ui.view.bangunan.InsertBgnView
import com.jodifrkh.asramaapp.ui.view.bangunan.UpdateBgnScreen
import com.jodifrkh.asramaapp.ui.view.kamar.HomeKmrScreen
import com.jodifrkh.asramaapp.ui.view.mahasiswa.HomeMhsScreen
import com.jodifrkh.asramaapp.ui.view.pembayaranSewa.HomePsScreen

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
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertBgn.route)
                },
                onDetailClick = { idBgn ->
                    Log.d("MainControllerPage", "Navigating to DetailBgn with idBgn: $idBgn")
                    navController.navigate("${DestinasiDetailBgn.route}/$idBgn")
                },
                onBackClick = {navController.popBackStack()}
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

        composable(DestinasiInsertBgn.route) {
            InsertBgnView(
                onBackClick = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
            )
        }

        composable(
            DestinasiDetailBgn.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailBgn.idBgn) {
                    type = NavType.StringType
                }
            )
        ) {
           val idBgn = it.arguments?.getString(DestinasiDetailBgn.idBgn)
            idBgn?.let { idBgn ->
                DetailBgnScreen(
                    onClickBack = {
                        navController.navigate(DestinasiHomeBgn.route) {
                            popUpTo(DestinasiHomeBgn.route) {
                                inclusive = true
                            }
                        }
                    },
                    onUpdateClick = {
                        navController.navigate("${DestinasiUpdateBgn.route}/$idBgn")
                    }
                )
            }
        }

        composable(
            DestinasiUpdateBgn.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateBgn.idBgn) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateBgnScreen(
                onNavigate = {navController.popBackStack()},
                onClickBack = {navController.popBackStack()}
            )
        }
    }
}