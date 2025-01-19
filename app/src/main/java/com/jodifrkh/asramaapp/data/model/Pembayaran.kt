package com.jodifrkh.asramaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pembayaran (
    @SerialName("id_pembayaran")
    val idPs : Int,
    @SerialName("id_mahasiswa")
    val idMhs : Int,
    val jumlah : Int,
    @SerialName("tanggal_pembayaran")
    val tgl : String,
    @SerialName("status_pembayaran")
    val status: String
)

@Serializable
data class PembayaranResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pembayaran>
)

@Serializable
data class PembayaranDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pembayaran
)
