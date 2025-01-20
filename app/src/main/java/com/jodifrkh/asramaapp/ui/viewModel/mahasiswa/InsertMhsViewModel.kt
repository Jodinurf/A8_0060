package com.jodifrkh.asramaapp.ui.viewModel.mahasiswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jodifrkh.asramaapp.data.model.Mahasiswa
import com.jodifrkh.asramaapp.data.repository.MahasiswaRepository

class InsertMhsViewModel (
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {
    var uiMhsState by mutableStateOf(InsertMhsUiState())
        private set
    fun updateInsertMhsState(insertMhsUiEvent: InsertMhsUiEvent) {
        uiMhsState = InsertMhsUiState(insertMhsUiEvent = insertMhsUiEvent)
    }

    private fun validateMhsFields() : Boolean {
        val event = uiMhsState.insertMhsUiEvent
        val errorMhsState = FormErrorMhsState(
            idKmr = if (event.idKmr.isNotEmpty()) null else "Masukkan Kamar",
            nim = if (event.nim.isNotEmpty()) null else "NIM mahasiswa tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama mahasiswa tidak boleh kosong",
            email = if (event.email.isNotEmpty()) null else "Email mahasiswa tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Nomor HP mahasiswa tidak boleh kosong",
        )

        uiMhsState = uiMhsState.copy(isMhsEntryValid = errorMhsState)
        return errorMhsState.isMhsValid()
    }

    suspend fun insertMhs() : Boolean {
        val currentMhsEvent = uiMhsState.insertMhsUiEvent

        return if (validateMhsFields()) {
            try {
                mahasiswaRepository.insertMahasiswa(currentMhsEvent.toMhs())
                uiMhsState = uiMhsState.copy(
                    snackBarMessage = "Data berhasil disimpan",
                    insertMhsUiEvent = InsertMhsUiEvent(),
                    isMhsEntryValid = FormErrorMhsState()
                )
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            uiMhsState = uiMhsState.copy(snackBarMessage = "Input tidak valid, periksa data kembali")
            false
        }
    }
    fun resetSnackBarMhsMessage() {
        uiMhsState = uiMhsState.copy(snackBarMessage = null)
    }
}

data class InsertMhsUiState(
    val insertMhsUiEvent: InsertMhsUiEvent = InsertMhsUiEvent(),
    val isMhsEntryValid: FormErrorMhsState = FormErrorMhsState(),
    val snackBarMessage: String? = null,
    val isEditing: Boolean = false,
)


data class FormErrorMhsState(
    val idKmr: String? = null,
    val nim: String? = null,
    val nama: String? = null,
    val email: String? = null,
    val noHp: String? = null,
) {
    fun isMhsValid() : Boolean {
        return idKmr == null && nim == null && nama == null &&
                email == null && noHp == null
    }
}

fun Mahasiswa.toInsertUiMhsEvent() : InsertMhsUiEvent = InsertMhsUiEvent (
    idMhs = idMhs.toString(),
    nim = nim,
    nama = nama,
    email = email,
    noHp = noHp,
    idKmr = idKmr.toString(),
)

fun Mahasiswa.toUiStateMhs() : InsertMhsUiState = InsertMhsUiState(
    insertMhsUiEvent = toInsertUiMhsEvent()
)

fun InsertMhsUiEvent.toMhs(): Mahasiswa = Mahasiswa (
    idMhs = idMhs.toIntOrNull() ?:0,
    nim = nim,
    nama = nama,
    email = email,
    noHp = noHp,
    idKmr = idKmr.toIntOrNull() ?:0,
)

data class InsertMhsUiEvent(
    val idMhs: String = "",
    val nim : String = "",
    val nama : String = "",
    val email : String = "",
    val noHp : String = "",
    val idKmr : String = ""
)