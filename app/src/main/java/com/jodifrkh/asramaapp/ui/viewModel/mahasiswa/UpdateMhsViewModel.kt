package com.jodifrkh.asramaapp.ui.viewModel.mahasiswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.repository.MahasiswaRepository
import com.jodifrkh.asramaapp.navigation.DestinasiUpdateMhs
import kotlinx.coroutines.launch

class UpdateMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {
    var updateMhsUiState by mutableStateOf(InsertMhsUiState())
        private set

    private val _idMhs: String = checkNotNull(savedStateHandle[DestinasiUpdateMhs.idMhs])

    fun updateInsertMhsState(insertMhsUiEvent: InsertMhsUiEvent) {
        if (!updateMhsUiState.isEditing || insertMhsUiEvent.idKmr == updateMhsUiState.insertMhsUiEvent.idKmr) {
            updateMhsUiState = InsertMhsUiState(insertMhsUiEvent = insertMhsUiEvent, isEditing = updateMhsUiState.isEditing)
        }
    }

    init {
        viewModelScope.launch {
            val mahasiswa = mahasiswaRepository.getMahasiswaById(_idMhs.toInt())
            updateMhsUiState = mahasiswa.toUiStateMhs().copy(isEditing = true)
        }
    }

    private fun validateMhsFields() : Boolean {
        val event = updateMhsUiState.insertMhsUiEvent
        val errorMhsState = FormErrorMhsState(
            idKmr = if (event.idKmr.isNotEmpty()) null else "Masukkan Kamar",
            nim = if (event.nim.isNotEmpty()) null else "NIM mahasiswa tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama mahasiswa tidak boleh kosong",
            email = if (event.email.isNotEmpty()) null else "Email mahasiswa tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Nomor HP mahasiswa tidak boleh kosong",
        )

        updateMhsUiState = updateMhsUiState.copy(isMhsEntryValid = errorMhsState)
        return errorMhsState.isMhsValid()
    }

    suspend fun updateMhs() : Boolean {
        val currentMhsEvent = updateMhsUiState.insertMhsUiEvent

        return if (validateMhsFields()) {
            try {
                mahasiswaRepository.updateMahasiswa(_idMhs.toInt(), currentMhsEvent.toMhs())

                updateMhsUiState = updateMhsUiState.copy(
                    snackBarMessage = "Data berhasil diupdate",
                    insertMhsUiEvent = InsertMhsUiEvent(),
                    isMhsEntryValid = FormErrorMhsState()
                )
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            updateMhsUiState = updateMhsUiState.copy(snackBarMessage = "Input tidak valid, periksa data kembali")
            false
        }
    }
    fun resetSnackBarMhsMessage() {
        updateMhsUiState = updateMhsUiState.copy(snackBarMessage = null)
    }
}
