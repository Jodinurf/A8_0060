package com.jodifrkh.asramaapp.ui.viewModel.kamar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jodifrkh.asramaapp.data.model.Kamar
import com.jodifrkh.asramaapp.data.repository.KamarRepository

class InsertKmrViewModel (
    private val kamarRepository: KamarRepository
) : ViewModel() {
    var uiKmrState by mutableStateOf(InsertKmrUiState())
        private set
    fun updateInsertKmrState(insertKmrUiEvent: InsertKmrUiEvent) {
        uiKmrState = InsertKmrUiState(insertKmrUiEvent = insertKmrUiEvent)
    }

    private fun validateKmrFields() : Boolean {
        val event = uiKmrState.insertKmrUiEvent
        val errorKmrState = FormErrorKmrState(
            idBgn = if (event.idBgn.isNotEmpty()) null else "Masukkan Bangunan",
            nomorKmr = if (event.nomorKmr.isNotEmpty()) null else "Nomor kamar tidak boleh kosong",
            kapasitas = if (event.kapasitas.isNotEmpty()) null else "Kapasitas tidak boleh kosong",
            statusKmr = if (event.statusKmr.isNotEmpty()) null else "Status kamar tidak boleh kosong",
        )

        uiKmrState = uiKmrState.copy(isKmrEntryValid = errorKmrState)
        return errorKmrState.isKmrValid()
    }

    suspend fun insertKmr() : Boolean {
        val currentKmrEvent = uiKmrState.insertKmrUiEvent

        return if (validateKmrFields()) {
            try {
                kamarRepository.insertKamar(currentKmrEvent.toKmr())
                uiKmrState = uiKmrState.copy(
                    snackBarMessage = "Data berhasil disimpan",
                    insertKmrUiEvent = InsertKmrUiEvent(),
                    isKmrEntryValid = FormErrorKmrState()
                )
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            uiKmrState = uiKmrState.copy(snackBarMessage = "Input tidak valid, periksa data kembali")
            false
        }
    }
    fun resetSnackBarKmrMessage() {
        uiKmrState = uiKmrState.copy(snackBarMessage = null)
    }
}

data class InsertKmrUiState(
    val insertKmrUiEvent: InsertKmrUiEvent = InsertKmrUiEvent(),
    val isKmrEntryValid: FormErrorKmrState = FormErrorKmrState(),
    val snackBarMessage: String? = null,
    val isEditing: Boolean = false,
)


data class FormErrorKmrState(
    val idBgn: String? = null,
    val nomorKmr: String? = null,
    val kapasitas: String? = null,
    val statusKmr: String? = null
) {
    fun isKmrValid() : Boolean {
        return idBgn == null && nomorKmr == null &&
                kapasitas == null && statusKmr == null
    }
}

fun Kamar.toInsertUiKmrEvent() : InsertKmrUiEvent = InsertKmrUiEvent (
    idKmr = idKmr.toString(),
    idBgn = idBgn.toString(),
    nomorKmr = nomorKmr,
    kapasitas = kapasitas.toString(),
    statusKmr = statusKmr
)

fun Kamar.toUiStateKmr() : InsertKmrUiState = InsertKmrUiState(
    insertKmrUiEvent = toInsertUiKmrEvent()
)

fun InsertKmrUiEvent.toKmr(): Kamar = Kamar (
    idKmr = idKmr.toIntOrNull() ?:0,
    idBgn = idBgn.toIntOrNull() ?:0,
    nomorKmr = nomorKmr,
    kapasitas = kapasitas.toIntOrNull() ?:0,
    statusKmr = statusKmr
)

data class InsertKmrUiEvent(
    val idKmr: String = "",
    val nomorKmr : String = "",
    val kapasitas : String = "",
    val statusKmr : String = "",
    val idBgn : String = ""
)