package com.jodifrkh.asramaapp.navigation

interface NavigateDestination {
    val route : String
    val titleRes: String
}

object DestinasiHomeMhs : NavigateDestination {
    override val route = "homeMhs"
    override val titleRes: String = "Homepage Bangunan"
}

object DestinasiHomeBgn : NavigateDestination {
    override val route = "homeBgn"
    override val titleRes: String = "Homepage Bangunan"
}

object DestinasiHomeKmr : NavigateDestination {
    override val route = "homeKmr"
    override val titleRes = "Homepage Kamar"
}

object DestinasiHomePS : NavigateDestination {
    override val route = "homePS"
    override val titleRes = "Homepage Pembayaran"
}