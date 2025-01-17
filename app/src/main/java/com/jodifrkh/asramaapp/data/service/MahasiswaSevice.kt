package com.jodifrkh.asramaapp.data.service

import com.jodifrkh.asramaapp.data.model.Mahasiswa
import com.jodifrkh.asramaapp.data.model.MahasiswaDetailResponse
import com.jodifrkh.asramaapp.data.model.MahasiswaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MahasiswaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("mahasiswa")
    suspend fun getMahasiswa(): MahasiswaResponse

    @GET("mahasiswa/{idMhs}")
    suspend fun getMahasiswaById(@Path("idMhs")idMhs:Int): MahasiswaDetailResponse

    @POST("mahasiswa/store")
    suspend fun insertMahasiswa(@Body mahasiswa: Mahasiswa)

    @PUT("mahasiswa/{idMhs}")
    suspend fun updateMahasiswa(@Path("idMhs")idMhs: Int, @Body mahasiswa: Mahasiswa)

    @DELETE("mahasiswa/{idMhs}")
    suspend fun deleteMahasiswa(@Path("idMhs")idMhs: Int): Response<Void>
}