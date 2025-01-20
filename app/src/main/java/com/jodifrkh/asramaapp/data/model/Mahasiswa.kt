package com.jodifrkh.asramaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mahasiswa (
    @SerialName("id_mahasiswa")
    val idMhs : Int,
    @SerialName("nomor_identitas")
    val nim : String,
    @SerialName("nama_mahasiswa")
    val nama : String,
    val email : String,
    @SerialName("nomor_telepon")
    val noHp : String,

    @SerialName("id_kamar")
    val idKmr : Int
)

@Serializable
data class MahasiswaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Mahasiswa>
)
@Serializable
data class MahasiswaDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Mahasiswa
)