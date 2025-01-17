package com.jodifrkh.asramaapp.data.service

import com.jodifrkh.asramaapp.data.model.Kamar
import com.jodifrkh.asramaapp.data.model.KamarDetailResponse
import com.jodifrkh.asramaapp.data.model.KamarResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KamarService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("kamar")
    suspend fun getKamar() : KamarResponse

    @GET("kamar/{idKmr}")
    suspend fun getKamarById(@Path("idKmr")idKmr : Int) : KamarDetailResponse

    @POST("kamar/store")
    suspend fun insertKamar(@Body kamar: Kamar)

    @PUT("kamar/{idKmr}")
    suspend fun updateKamar(@Path("idKmr")idKmr: Int, @Body kamar: Kamar)

    @DELETE("kamar/{idKmr}")
    suspend fun deleteKamar(@Path("idKmr")idKmr: Int): Response<Void>
}