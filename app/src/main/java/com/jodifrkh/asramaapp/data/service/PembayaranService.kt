package com.jodifrkh.asramaapp.data.service

import com.jodifrkh.asramaapp.data.model.Pembayaran
import com.jodifrkh.asramaapp.data.model.PembayaranDetailResponse
import com.jodifrkh.asramaapp.data.model.PembayaranResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PembayaranService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("pembayaran")
    suspend fun getPembayaran(): PembayaranResponse

    @GET("pembayaran/{idPs}")
    suspend fun getPembayaranById(@Path("idPs")idPs:Int): PembayaranDetailResponse

    @GET("pembayaran/mahasiswa/{idMhs}")
    suspend fun getPembayaranByIdMhs(@Path("idMhs")idMhs:Int) : PembayaranDetailResponse

    @POST("pembayaran/store")
    suspend fun insertPembayaran(@Body pembayaran: Pembayaran)

    @PUT("pembayaran/{idPs}")
    suspend fun updatePembayaran(@Path("idPs")idPs: Int, @Body pembayaran: Pembayaran)

    @DELETE("pembayaran/{idPs}")
    suspend fun deletePembayaran(@Path("idPs")idPs: Int): Response<Void>
}