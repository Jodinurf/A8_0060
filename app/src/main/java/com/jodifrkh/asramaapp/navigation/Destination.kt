package com.jodifrkh.asramaapp.navigation

interface NavigateDestination {
    val route : String
    val titleRes: String
}

object DestinasiHomepage : NavigateDestination {
    override val route = "homepage"
    override val titleRes = "Homepage"
}

// ------------------Bangunan------------------- //
object DestinasiHomeBgn : NavigateDestination {
    override val route = "homeBgn"
    override val titleRes: String = "Daftar Bangunan"
}

object DestinasiInsertBgn : NavigateDestination {
    override val route = "insertBgn"
    override val titleRes = "Tambah Data Bangunan"
}

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

// ------------------Kamar------------------- //
object DestinasiHomeKmr : NavigateDestination {
    override val route = "homeKmr"
    override val titleRes = "Daftar Kamar"
}

object DestinasiInsertKmr : NavigateDestination {
    override val route = "insertKmr"
    override val titleRes = "Tambah Data Kamar"
}

object DestinasiDetailKmr : NavigateDestination {
    override val route = "detailKmr"
    override val titleRes = "Data Kamar"
    const val idKmr = "idKmr"
    val routesWithArg = "$route/{$idKmr}"
}

object DestinasiUpdateKmr : NavigateDestination {
    override val route = "updateKmr"
    override val titleRes = "Update Kamar"
    const val idKmr = "idKmr"
    val routesWithArg = "$route/{$idKmr}"
}

// ------------------Mahasiswa------------------- //
object DestinasiHomeMhs : NavigateDestination {
    override val route = "homeMhs"
    override val titleRes: String = "Daftar Mahasiswa"
}
object DestinasiInsertMhs : NavigateDestination {
    override val route = "insertMhs"
    override val titleRes = "Tambah Data Mahasiswa"
}

object DestinasiDetailMhs : NavigateDestination {
    override val route = "detailMhs"
    override val titleRes = "Data Mahasiswa"
    const val idMhs = "idMhs"
    val routesWithArg = "$route/{$idMhs}"
}

object DestinasiUpdateMhs : NavigateDestination {
    override val route = "updateMhs"
    override val titleRes = "Update Mahasiswa"
    const val idMhs = "idMhs"
    val routesWithArg = "$route/{$idMhs}"
}

// ------------------Transaksi------------------- //
object DestinasiHomePS : NavigateDestination {
    override val route = "homePS"
    override val titleRes = "Daftar Pembayaran"
}
