package com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jodifrkh.asramaapp.data.model.Pembayaran
import com.jodifrkh.asramaapp.data.repository.PembayaranRepository

class InsertPSViewModel (
    private val pembayaranRepository: PembayaranRepository
) : ViewModel() {
    var uiPSState by mutableStateOf(InsertPSUiState())
        private set
    fun updateInsertPSState(insertPSUiEvent: InsertPSUiEvent) {
        uiPSState = InsertPSUiState(insertPSUiEvent = insertPSUiEvent)
    }

    private fun validatePSFields() : Boolean {
        val event = uiPSState.insertPSUiEvent
        val errorPSState = FormErrorPSState(
            idMhs = if (event.idMhs.isNotEmpty()) null else "Mahasiswa tidak boleh kosong",
            jumlah = if (event.jumlah.isNotEmpty()) null else "Jumlah Pembayaran tidak boleh kosong",
            tgl = if (event.tgl.isNotEmpty()) null else "Tanggal Pembayaran tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status Pembayaran tidak boleh kosong",
        )

        uiPSState = uiPSState.copy(isPSEntryValid = errorPSState)
        return errorPSState.isPSValid()
    }

    suspend fun insertPS() : Boolean {
        val currentPSEvent = uiPSState.insertPSUiEvent

        return if (validatePSFields()) {
            try {
                pembayaranRepository.insertPembayaran(currentPSEvent.toPS())
                uiPSState = uiPSState.copy(
                    snackBarMessage = "Data berhasil disimpan",
                    insertPSUiEvent = InsertPSUiEvent(),
                    isPSEntryValid = FormErrorPSState()
                )
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            uiPSState = uiPSState.copy(snackBarMessage = "Input tidak valid, periksa data kembali")
            false
        }
    }
    fun resetSnackBarPSMessage() {
        uiPSState = uiPSState.copy(snackBarMessage = null)
    }
}

data class InsertPSUiState(
    val insertPSUiEvent: InsertPSUiEvent = InsertPSUiEvent(),
    val isPSEntryValid: FormErrorPSState = FormErrorPSState(),
    val snackBarMessage: String? = null,
    val isEditing: Boolean = true,
)


data class FormErrorPSState(
    val idMhs : String ? = null,
    val jumlah : String ? = null,
    val tgl : String ? = null,
    val status : String ? = null,
) {
    fun isPSValid() : Boolean {
        return idMhs == null && jumlah == null &&
                tgl == null && status == null
    }
}

fun Pembayaran.toInsertUiPSEvent() : InsertPSUiEvent = InsertPSUiEvent (
    idPs = idPs.toString(),
    idMhs = idMhs.toString(),
    jumlah = jumlah.toString(),
    tgl = tgl,
    status = status
)

fun Pembayaran.toUiStatePS() : InsertPSUiState = InsertPSUiState(
    insertPSUiEvent = toInsertUiPSEvent()
)

fun InsertPSUiEvent.toPS(): Pembayaran = Pembayaran (
    idPs = idPs.toIntOrNull() ?:0,
    idMhs = idMhs.toIntOrNull() ?:0,
    jumlah = jumlah.toIntOrNull() ?:0,
    tgl = tgl,
    status = status
)

data class InsertPSUiEvent(
    val idPs: String = "",
    val idMhs : String = "",
    val jumlah : String = "",
    val tgl : String = "",
    val status : String = ""
)