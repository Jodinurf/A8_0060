package com.jodifrkh.asramaapp.ui.viewModel.bangunan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.repository.BangunanRepository
import com.jodifrkh.asramaapp.navigation.DestinasiUpdateBgn
import kotlinx.coroutines.launch

class UpdateBgnViewModel(
    savedStateHandle: SavedStateHandle,
    private val bangunanRepository: BangunanRepository
) : ViewModel () {
    var updateBgnUiState by mutableStateOf(InsertBgnUiState())
        private set

    private val _idBgn : String = checkNotNull(savedStateHandle[DestinasiUpdateBgn.idBgn])

    fun updateInsertBgnState(insertBgnUiEvent: InsertBgnUiEvent) {
        updateBgnUiState = InsertBgnUiState(insertBgnUiEvent = insertBgnUiEvent)
    }

    init {
        viewModelScope.launch {
            updateBgnUiState = bangunanRepository.getBangunanById(_idBgn.toInt())
                .toUiStateBgn()
        }
    }

    private fun validateBgnFields() : Boolean {
        val event = updateBgnUiState.insertBgnUiEvent
        val errorBgnState = FormErrorBgnState(
            namaBgn = if (event.namaBgn.isNotEmpty()) null else "Nama Bangunan Tidak Boleh Kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            jmlhLantai = if (event.jmlhLantai.isNotEmpty()) null else "Jumlah lantai tidak boleh kosong",
        )

        updateBgnUiState = updateBgnUiState.copy(isBgnEntryValid = errorBgnState)
        return errorBgnState.isBgnValid()
    }

    suspend fun updateBgn() : Boolean {
        val currentBgnEvent = updateBgnUiState.insertBgnUiEvent

        return if (validateBgnFields()) {
            try {
                bangunanRepository.updateBangunan(_idBgn.toInt(), currentBgnEvent.toBgn())

                updateBgnUiState = updateBgnUiState.copy(
                    snackBarMessage = "Data berhasil diupdate",
                    insertBgnUiEvent = InsertBgnUiEvent(),
                    isBgnEntryValid = FormErrorBgnState()
                )
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            updateBgnUiState = updateBgnUiState.copy(snackBarMessage = "Input tidak valid, periksa data kembali")
            false
        }
    }
    fun resetSnackBarBgnMessage() {
        updateBgnUiState = updateBgnUiState.copy(snackBarMessage = null)
    }
}
