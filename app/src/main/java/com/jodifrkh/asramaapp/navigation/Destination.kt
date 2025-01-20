package com.jodifrkh.asramaapp.navigation

interface NavigateDestination {
    val route : String
    val titleRes: String
}

// Homepage

object DestinasiHomeMhs : NavigateDestination {
    override val route = "homeMhs"
    override val titleRes: String = "Homepage Mahasiswa"
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

// Insert page

object DestinasiInsertBgn : NavigateDestination {
    override val route = "insertBgn"
    override val titleRes = "Tambah Data Bangunan"
}

// Detail Page
object DestinasiDetailBgn : NavigateDestination {
    override val route = "detailBgn"
    override val titleRes = "Data Bangunan"
    const val idBgn = "idBgn"
    val routesWithArg = "$route/{$idBgn}"
}

object DestinasiUpdateBgn : NavigateDestination {
    override val route = "updateBgn"
    override val titleRes = "Update Bangunan"
    const val idBgn = "idBgn"
    val routesWithArg = "$route/{$idBgn}"
}