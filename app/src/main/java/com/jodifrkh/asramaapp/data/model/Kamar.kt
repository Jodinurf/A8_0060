package com.jodifrkh.asramaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kamar(
    @SerialName("id_kamar")
    val idKmr : Int,
    @SerialName("id_bangunan")
    val idBgn : Int,
    @SerialName("nomor_kamar")
    val nomorKmr : String,
    val kapasitas : Int,
    @SerialName("status_kamar")
    val statusKmr : String
)

@Serializable
data class KamarResponse(
    val status: Boolean,
    val message: String,
    val data: List<Kamar>
)

@Serializable
data class KamarDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Kamar
)