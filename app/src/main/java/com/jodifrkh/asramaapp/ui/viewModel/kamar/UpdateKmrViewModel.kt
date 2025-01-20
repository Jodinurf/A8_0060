package com.jodifrkh.asramaapp.ui.viewModel.kamar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.repository.KamarRepository
import com.jodifrkh.asramaapp.navigation.DestinasiUpdateKmr
import kotlinx.coroutines.launch

class UpdateKmrViewModel(
    savedStateHandle: SavedStateHandle,
    private val kamarRepository: KamarRepository
) : ViewModel() {
    var updateKmrUiState by mutableStateOf(InsertKmrUiState())
        private set

    private val _idKmr : String = checkNotNull(savedStateHandle[DestinasiUpdateKmr.idKmr])

    fun updateInsertKmrState(insertKmrUiEvent: InsertKmrUiEvent) {
        if (!updateKmrUiState.isEditing || insertKmrUiEvent.idBgn == updateKmrUiState.insertKmrUiEvent.idBgn) {
            updateKmrUiState = InsertKmrUiState(insertKmrUiEvent = insertKmrUiEvent, isEditing = updateKmrUiState.isEditing)
        }
    }


    init {
        viewModelScope.launch {
            val kamar = kamarRepository.getKamarById(_idKmr.toInt())
            updateKmrUiState = kamar.toUiStateKmr().copy(isEditing = true)
        }
    }


    private fun validateKmrFields() : Boolean {
        val event = updateKmrUiState.insertKmrUiEvent
        val errorKmrState = FormErrorKmrState(
            idBgn = if (event.idBgn.isNotEmpty()) null else "Masukkan Bangunan",
            nomorKmr = if (event.nomorKmr.isNotEmpty()) null else "Nomor kamar tidak boleh kosong",
            kapasitas = if (event.kapasitas.isNotEmpty()) null else "Kapasitas tidak boleh kosong",
            statusKmr = if (event.statusKmr.isNotEmpty()) null else "Status kamar tidak boleh kosong",
        )

        updateKmrUiState = updateKmrUiState.copy(isKmrEntryValid = errorKmrState)
        return errorKmrState.isKmrValid()
    }

    suspend fun updateKmr() : Boolean {
        val currentKmrEvent = updateKmrUiState.insertKmrUiEvent

        return if (validateKmrFields()) {
            try {
                kamarRepository.updateKamar(_idKmr.toInt(), currentKmrEvent.toKmr())

                updateKmrUiState = updateKmrUiState.copy(
                    snackBarMessage = "Data berhasil diupdate",
                    insertKmrUiEvent = InsertKmrUiEvent(),
                    isKmrEntryValid = FormErrorKmrState()
                )
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            updateKmrUiState = updateKmrUiState.copy(snackBarMessage = "Input tidak valid, periksa data kembali")
            false
        }
    }
    fun resetSnackBarKmrMessage() {
        updateKmrUiState = updateKmrUiState.copy(snackBarMessage = null)
    }
}

