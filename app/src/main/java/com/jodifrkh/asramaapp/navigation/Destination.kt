package com.jodifrkh.asramaapp.navigation

interface NavigateDestination {
    val route : String
    val titleRes: String
}

object DestinasiHome : NavigateDestination {
    override val route = "home"
    override val titleRes: String = "Home Page"
}