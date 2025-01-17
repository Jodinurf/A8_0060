package com.jodifrkh.asramaapp.navigation

interface NavigateDestination {
    val route : String
    val titleRes: String
}

object DestinasiHomeMhs : NavigateDestination {
    override val route = "homeMhs"
    override val titleRes: String = "Home Page Bangunan"
}

object DestinasiHomeBgn : NavigateDestination {
    override val route = "homeBgn"
    override val titleRes: String = "Home Page Bangunan"
}