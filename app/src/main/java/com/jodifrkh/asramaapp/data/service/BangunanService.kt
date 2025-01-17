package com.jodifrkh.asramaapp.data.service

import com.jodifrkh.asramaapp.data.model.Bangunan
import com.jodifrkh.asramaapp.data.model.BangunanDetailResponse
import com.jodifrkh.asramaapp.data.model.BangunanResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BangunanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("bangunan")
    suspend fun getBangunan() : BangunanResponse

    @GET("bangunan/{idBgn}")
    suspend fun getBangunanById(@Path("idBgn")idBgn : Int) : BangunanDetailResponse

    @POST("bangunan/store")
    suspend fun insertBangunan(@Body bangunan: Bangunan)

    @PUT("bangunan/{idBgn}")
    suspend fun updateBangunan(@Path("idBgn")idBgn: Int, @Body bangunan: Bangunan)

    @DELETE("bangunan/{idBgn}")
    suspend fun deleteBangunan(@Path("idBgn")idBgn: Int): Response<Void>
}