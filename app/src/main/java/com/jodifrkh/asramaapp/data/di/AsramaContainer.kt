package com.jodifrkh.asramaapp.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jodifrkh.asramaapp.data.repository.BangunanRepository
import com.jodifrkh.asramaapp.data.repository.KamarRepository
import com.jodifrkh.asramaapp.data.repository.MahasiswaRepository
import com.jodifrkh.asramaapp.data.repository.NetworkBangunanRepository
import com.jodifrkh.asramaapp.data.repository.NetworkKamarRepository
import com.jodifrkh.asramaapp.data.repository.NetworkMahasiswaRepository
import com.jodifrkh.asramaapp.data.repository.NetworkPembayaranRepository
import com.jodifrkh.asramaapp.data.repository.PembayaranRepository
import com.jodifrkh.asramaapp.data.service.BangunanService
import com.jodifrkh.asramaapp.data.service.KamarService
import com.jodifrkh.asramaapp.data.service.MahasiswaService
import com.jodifrkh.asramaapp.data.service.PembayaranService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val mahasiswaRepository : MahasiswaRepository
    val bangunanRepository : BangunanRepository
    val kamarRepository : KamarRepository
    val pembayaranRepository : PembayaranRepository
}

class AsramaContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()


    private val mahasiswaService : MahasiswaService by lazy {
        retrofit.create(MahasiswaService :: class.java)
    }

    private val bangunanService : BangunanService by lazy {
        retrofit.create(BangunanService :: class.java)
    }

    private val kamarService : KamarService by lazy {
        retrofit.create(KamarService :: class.java)
    }

    private val pembayaranService : PembayaranService by lazy {
        retrofit.create(PembayaranService :: class.java)
    }

    override val mahasiswaRepository : MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(mahasiswaService)
    }
    override val bangunanRepository: BangunanRepository by lazy {
        NetworkBangunanRepository(bangunanService)
    }
    override val kamarRepository: KamarRepository by lazy {
        NetworkKamarRepository(kamarService)
    }

    override val pembayaranRepository : PembayaranRepository by lazy {
        NetworkPembayaranRepository(pembayaranService)
    }
}