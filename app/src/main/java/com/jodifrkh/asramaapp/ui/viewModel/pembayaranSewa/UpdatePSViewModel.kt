package com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.repository.PembayaranRepository
import com.jodifrkh.asramaapp.navigation.DestinasiUpdatePS
import kotlinx.coroutines.launch

class UpdatePSViewModel(
    savedStateHandle: SavedStateHandle,
    private val pembayaranRepository: PembayaranRepository
) : ViewModel() {
    var updatePSUiState by mutableStateOf(InsertPSUiState())
        private set

    private val _idPs : String = checkNotNull(savedStateHandle[DestinasiUpdatePS.idPs])

    fun updateInsertPSState(insertPSUiEvent: InsertPSUiEvent) {
        if (!updatePSUiState.isEditing || insertPSUiEvent.idMhs == updatePSUiState.insertPSUiEvent.idPs) {
            updatePSUiState = InsertPSUiState(insertPSUiEvent = insertPSUiEvent, isEditing = updatePSUiState.isEditing)
        }
    }

    init {
        viewModelScope.launch {
            val pembayaran = pembayaranRepository.getPembayaranById(_idPs.toInt())
            updatePSUiState = pembayaran.toUiStatePS().copy(isEditing = false)
        }
    }

    private fun validatePSFields() : Boolean {
        val event = updatePSUiState.insertPSUiEvent
        val errorPSState = FormErrorPSState(
            idMhs = if (event.idMhs.isNotEmpty()) null else "Mahasiswa tidak boleh kosong",
            jumlah = if (event.jumlah.isNotEmpty()) null else "Jumlah Pembayaran tidak boleh kosong",
            tgl = if (event.tgl.isNotEmpty()) null else "Tanggal Pembayaran tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status Pembayaran tidak boleh kosong",
        )
        updatePSUiState = updatePSUiState.copy(isPSEntryValid = errorPSState)
        return errorPSState.isPSValid()
    }

    suspend fun updatePembayaran() : Boolean {
        val currentPSEvent = updatePSUiState.insertPSUiEvent

        return if (validatePSFields()) {
            try {
                pembayaranRepository.updatePembayaran(_idPs.toInt(), currentPSEvent.toPS())

                updatePSUiState = updatePSUiState.copy(
                    snackBarMessage = "Data berhasil diupdate",
                    insertPSUiEvent = InsertPSUiEvent(),
                    isPSEntryValid = FormErrorPSState()
                )
                true
            } catch (e:Exception) {
                e.printStackTrace()
                false
            }
        } else {
            updatePSUiState = updatePSUiState.copy(snackBarMessage = "Input tidak valid, periksa data kembali")
            false
        }
    }
    fun resetSnackBarPSMessage() {
        updatePSUiState = updatePSUiState.copy(snackBarMessage = null)
    }

}