package com.jodifrkh.asramaapp.ui.viewModel.bangunan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jodifrkh.asramaapp.data.model.Bangunan
import com.jodifrkh.asramaapp.data.repository.BangunanRepository

class InsertBgnViewModel (
    private val bangunanRepository: BangunanRepository
) : ViewModel() {
    var uiBgnState by mutableStateOf(InsertBgnUiState())
        private set
    fun updateInsertBgnState(insertBgnUiEvent: InsertBgnUiEvent) {
        uiBgnState = InsertBgnUiState(insertBgnUiEvent = insertBgnUiEvent)
    }

    private fun validateBgnFields() : Boolean {
        val event = uiBgnState.insertBgnUiEvent
        val errorBgnState = FormErrorBgnState(
            namaBgn = if (event.namaBgn.isNotEmpty()) null else "Nama Bangunan Tidak Boleh Kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            jmlhLantai = if (event.jmlhLantai.isNotEmpty()) null else "Jumlah lantai tidak boleh kosong",
        )

        uiBgnState = uiBgnState.copy(isBgnEntryValid = errorBgnState)
        return errorBgnState.isBgnValid()
    }

    suspend fun insertBgn() : Boolean {
        val currentBgnEvent = uiBgnState.insertBgnUiEvent

        return if (validateBgnFields()) {
                try {
                    bangunanRepository.insertBangunan(currentBgnEvent.toBgn())
                    uiBgnState = uiBgnState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        insertBgnUiEvent = InsertBgnUiEvent(),
                        isBgnEntryValid = FormErrorBgnState()
                    )
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
        } else {
            uiBgnState = uiBgnState.copy(snackBarMessage = "Input tidak valid, periksa data kembali")
            false
        }
    }
    fun resetSnackBarBgnMessage() {
        uiBgnState = uiBgnState.copy(snackBarMessage = null)
    }
}

data class InsertBgnUiState(
    val insertBgnUiEvent: InsertBgnUiEvent = InsertBgnUiEvent(),
    val isBgnEntryValid : FormErrorBgnState = FormErrorBgnState(),
    val snackBarMessage : String? = null
)

data class FormErrorBgnState(
    val namaBgn: String? = null,
    val alamat: String? = null,
    val jmlhLantai: String? = null
) {
    fun isBgnValid() : Boolean {
        return namaBgn == null && alamat == null &&
                jmlhLantai == null
    }
}

fun Bangunan.toInsertUiBgnEvent() : InsertBgnUiEvent = InsertBgnUiEvent (
    idBgn = idBgn.toString(),
    namaBgn = namaBgn,
    alamat = alamat,
    jmlhLantai = jmlhLantai.toString()
)

fun Bangunan.toUiStateBgn() : InsertBgnUiState = InsertBgnUiState(
    insertBgnUiEvent = toInsertUiBgnEvent()
)

fun InsertBgnUiEvent.toBgn(): Bangunan = Bangunan (
    idBgn = idBgn.toIntOrNull() ?:0,
    namaBgn = namaBgn,
    alamat = alamat,
    jmlhLantai = jmlhLantai.toIntOrNull() ?:0
)

data class InsertBgnUiEvent(
    val idBgn: String = "",
    val namaBgn: String = "",
    val alamat: String = "",
    val jmlhLantai: String = ""
)