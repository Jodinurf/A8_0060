package com.jodifrkh.asramaapp.data.repository

import com.jodifrkh.asramaapp.data.model.Kamar
import com.jodifrkh.asramaapp.data.model.KamarResponse
import com.jodifrkh.asramaapp.data.service.KamarService
import okio.IOException

interface KamarRepository{
    suspend fun getKamar() : KamarResponse
    suspend fun insertKamar(kamar: Kamar)
    suspend fun updateKamar(idKmr: Int, kamar: Kamar)
    suspend fun deleteKamar(idKmr: Int)
    suspend fun getKamarById(idKmr: Int): Kamar
}

class NetworkKamarRepository(
    private val kamarApiService : KamarService
) : KamarRepository {
    override suspend fun getKamar(): KamarResponse {
        return try {
            kamarApiService.getKamar()
        } catch (e: Exception) {
            throw IOException("Failed to fetch kamar data : ${e.message}")
        }
    }

    override suspend fun insertKamar(kamar: Kamar) {
        kamarApiService.insertKamar(kamar)
    }

    override suspend fun updateKamar(idKmr: Int, kamar: Kamar) {
        kamarApiService.updateKamar(idKmr, kamar)
    }

    override suspend fun deleteKamar(idKmr: Int) {
        try {
            val response = kamarApiService.deleteKamar(idKmr)
            if (!response.isSuccessful) {
                throw IOException("gagal menghapus data kamar. HTTP kode: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKamarById(idKmr: Int): Kamar {
        return kamarApiService.getKamarById(idKmr).data
    }

}
