package com.jodifrkh.asramaapp.data.repository

import com.jodifrkh.asramaapp.data.model.Mahasiswa
import com.jodifrkh.asramaapp.data.model.MahasiswaResponse
import com.jodifrkh.asramaapp.data.service.MahasiswaService
import okio.IOException

interface MahasiswaRepository{
    suspend fun getMahasiswa() : MahasiswaResponse
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)
    suspend fun updateMahasiswa(idMhs: Int, mahasiswa: Mahasiswa)
    suspend fun deleteMahasiswa(idMhs: Int)
    suspend fun getMahasiswaById(idMhs: Int): Mahasiswa
}

class NetworkMahasiswaRepository(
    private val mahasiswaApiService : MahasiswaService
) : MahasiswaRepository {
    override suspend fun getMahasiswa(): MahasiswaResponse {
        return try {
            mahasiswaApiService.getMahasiswa()
        } catch (e: Exception) {
            throw IOException("Failed to fetch Mahasiswa data: ${e.message}")
        }
    }

    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        mahasiswaApiService.insertMahasiswa(mahasiswa)
    }

    override suspend fun updateMahasiswa(idMhs: Int, mahasiswa: Mahasiswa) {
        mahasiswaApiService.updateMahasiswa(idMhs, mahasiswa)
    }

    override suspend fun deleteMahasiswa(idMhs: Int) {
        try {
            val response = mahasiswaApiService.deleteMahasiswa(idMhs)
            if (!response.isSuccessful) {
                throw IOException("gagal menghapus data Mahasiswa. HTTP kode: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMahasiswaById(idMhs: Int): Mahasiswa {
        return mahasiswaApiService.getMahasiswaByNim(idMhs).data
    }
}