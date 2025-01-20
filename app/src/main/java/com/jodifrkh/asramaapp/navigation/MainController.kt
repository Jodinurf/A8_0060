package com.jodifrkh.asramaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jodifrkh.asramaapp.ui.view.Homepage
import com.jodifrkh.asramaapp.ui.view.bangunan.DetailBgnScreen
import com.jodifrkh.asramaapp.ui.view.bangunan.HomeBgnScreen
import com.jodifrkh.asramaapp.ui.view.bangunan.InsertBgnView
import com.jodifrkh.asramaapp.ui.view.bangunan.UpdateBgnScreen
import com.jodifrkh.asramaapp.ui.view.kamar.DetailKmrScreen
import com.jodifrkh.asramaapp.ui.view.kamar.HomeKmrScreen
import com.jodifrkh.asramaapp.ui.view.kamar.InsertKmrView
import com.jodifrkh.asramaapp.ui.view.kamar.UpdateKmrScreen
import com.jodifrkh.asramaapp.ui.view.mahasiswa.DetailMhsScreen
import com.jodifrkh.asramaapp.ui.view.mahasiswa.HomeMhsScreen
import com.jodifrkh.asramaapp.ui.view.mahasiswa.InsertMhsView
import com.jodifrkh.asramaapp.ui.view.mahasiswa.UpdateMhsScreen
import com.jodifrkh.asramaapp.ui.view.pembayaranSewa.HomePsScreen

@Composable
fun MainControllerPage(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomepage.route
    ) {

        composable(DestinasiHomepage.route) {
            Homepage(
                onItemClick = { item ->
                    when (item) {
                        "Bangunan" -> navController.navigate(DestinasiHomeBgn.route)
                        "Kamar" -> navController.navigate(DestinasiHomeKmr.route)
                        "Mahasiswa" -> navController.navigate(DestinasiHomeMhs.route)
                        "Pembayaran Sewa" -> navController.navigate(DestinasiHomePS.route)
                        else -> {}
                    }
                }
            )
        }

        // ------------------Bangunan------------------- //
        composable(DestinasiHomeBgn.route) {
            HomeBgnScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertBgn.route)
                },
                onDetailClick = { idBgn ->
                    navController.navigate("${DestinasiDetailBgn.route}/$idBgn")
                },
                onBackClick = {navController.popBackStack()}
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

        // ------------------Kamar------------------- //
        composable(DestinasiHomeKmr.route) {
            HomeKmrScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertKmr.route)
                },
                onDetailClick = { idKmr ->
                    navController.navigate("${DestinasiDetailKmr.route}/$idKmr")
                },
                onBackClick = {navController.popBackStack()}
            )
        }

        composable(DestinasiInsertKmr.route) {
            InsertKmrView(
                onBackClick = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
            )
        }

        composable(
            DestinasiDetailKmr.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailKmr.idKmr) {
                    type = NavType.StringType
                }
            )
        ) {
            val idKmr = it.arguments?.getString(DestinasiDetailKmr.idKmr)
            idKmr?.let { idKmr ->
                DetailKmrScreen(
                    onClickBack = {
                        navController.navigate(DestinasiHomeKmr.route) {
                            popUpTo(DestinasiHomeKmr.route) {
                                inclusive = true
                            }
                        }
                    },
                    onUpdateClick = {
                        navController.navigate("${DestinasiUpdateKmr.route}/$idKmr")
                    }
                )
            }
        }

        composable(
            DestinasiUpdateKmr.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateKmr.idKmr) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateKmrScreen(
                onNavigate = {navController.popBackStack()},
                onClickBack = {navController.popBackStack()}
            )
        }

        // ------------------Mahasiswa------------------- //
        composable(DestinasiHomeMhs.route) {
            HomeMhsScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertMhs.route)
                },
                onDetailClick = { idMhs ->
                    navController.navigate("${DestinasiDetailMhs.route}/$idMhs")
                },
                onBackClick = {navController.popBackStack()},
                onEditClick = {idMhs ->
                    navController.navigate("${DestinasiUpdateMhs.route}/$idMhs")
                }
            )
        }
        composable(DestinasiInsertMhs.route) {
            InsertMhsView(
                onBackClick = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
            )
        }
        composable(
            DestinasiDetailMhs.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMhs.idMhs) {
                    type = NavType.StringType
                }
            )
        ) {
            val idMhs = it.arguments?.getString(DestinasiDetailMhs.idMhs)
            idMhs?.let { idMhs ->
                DetailMhsScreen(
                    onClickBack = {
                        navController.navigate(DestinasiHomeMhs.route) {
                            popUpTo(DestinasiHomeMhs.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiUpdateMhs.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateMhs.idMhs) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateMhsScreen(
                onNavigate = {navController.popBackStack()},
                onClickBack = {navController.popBackStack()}
            )
        }

        // ------------------Transaksi------------------- //
        composable(DestinasiHomePS.route) {
            HomePsScreen(
                navigateToItemEntry = {},
                onDetailClick = {}
            )
        }
    }
}