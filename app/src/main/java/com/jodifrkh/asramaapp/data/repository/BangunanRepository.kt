package com.jodifrkh.asramaapp.data.repository

import com.jodifrkh.asramaapp.data.model.Bangunan
import com.jodifrkh.asramaapp.data.model.BangunanResponse
import com.jodifrkh.asramaapp.data.service.BangunanService
import okio.IOException

interface BangunanRepository{
    suspend fun getBangunan() : BangunanResponse
    suspend fun insertBangunan(bangunan: Bangunan)
    suspend fun updateBangunan(idBgn: Int, bangunan: Bangunan)
    suspend fun deleteBangunan(idBgn: Int)
    suspend fun getBangunanById(idBgn: Int): Bangunan
}

class NetworkBangunanRepository(
    private val bangunanApiService : BangunanService
) : BangunanRepository {
    override suspend fun getBangunan(): BangunanResponse {
        return try {
            bangunanApiService.getBangunan()
        } catch (e: Exception) {
            throw IOException("Failed to fetch Bangunan data : ${e.message}")
        }
    }

    override suspend fun insertBangunan(bangunan: Bangunan) {
        bangunanApiService.insertBangunan(bangunan)
    }

    override suspend fun updateBangunan(idBgn: Int, bangunan: Bangunan) {
        bangunanApiService.updateBangunan(idBgn, bangunan)
    }

    override suspend fun deleteBangunan(idBgn: Int) {
        try {
            val response = bangunanApiService.deleteBangunan(idBgn)
            if (!response.isSuccessful) {
                throw IOException("gagal menghapus data Bangunan. HTTP kode: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBangunanById(idBgn: Int): Bangunan {
        return bangunanApiService.getBangunanById(idBgn).data
    }

}
