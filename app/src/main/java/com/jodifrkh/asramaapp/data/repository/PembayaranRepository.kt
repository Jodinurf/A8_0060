package com.jodifrkh.asramaapp.data.repository

import com.jodifrkh.asramaapp.data.model.Pembayaran
import com.jodifrkh.asramaapp.data.model.PembayaranResponse
import com.jodifrkh.asramaapp.data.service.PembayaranService
import okio.IOException

interface PembayaranRepository{
    suspend fun getPembayaran() : PembayaranResponse
    suspend fun insertPembayaran(pembayaran: Pembayaran)
    suspend fun updatePembayaran(idPs: Int, pembayaran: Pembayaran)
    suspend fun deletePembayaran(idPs: Int)
    suspend fun getPembayaranById(idPs: Int): Pembayaran
    suspend fun getPembayaranByIdMhs(idMhs: Int): Pembayaran
}

class NetworkPembayaranRepository(
    private val PembayaranApiService : PembayaranService
) : PembayaranRepository {
    override suspend fun getPembayaran(): PembayaranResponse {
        return try {
            PembayaranApiService.getPembayaran()
        } catch (e: Exception) {
            throw IOException("Failed to fetch Pembayaran data: ${e.message}")
        }
    }

    override suspend fun insertPembayaran(pembayaran: Pembayaran) {
        PembayaranApiService.insertPembayaran(pembayaran)
    }

    override suspend fun updatePembayaran(idPs: Int, pembayaran: Pembayaran) {
        PembayaranApiService.updatePembayaran(idPs, pembayaran)
    }

    override suspend fun deletePembayaran(idPs: Int) {
        try {
            val response = PembayaranApiService.deletePembayaran(idPs)
            if (!response.isSuccessful) {
                throw IOException("gagal menghapus data Pembayaran. HTTP kode: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPembayaranById(idPs: Int): Pembayaran {
        return PembayaranApiService.getPembayaranById(idPs).data
    }

    override suspend fun getPembayaranByIdMhs(idMhs: Int): Pembayaran {
        return PembayaranApiService.getPembayaranByIdMhs(idMhs).data
    }
}