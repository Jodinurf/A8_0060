package com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.model.Pembayaran
import com.jodifrkh.asramaapp.data.repository.PembayaranRepository
import com.jodifrkh.asramaapp.navigation.DestinasiDetailPS
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailPsUiState {
    data class Success(val pembayaran: Pembayaran) : DetailPsUiState()
    object Error : DetailPsUiState()
    object Loading : DetailPsUiState()
}

class DetailPsViewModel(
    savedStateHandle: SavedStateHandle,
    private val ps: PembayaranRepository
) : ViewModel() {

    private val _idPs: String? = savedStateHandle[DestinasiDetailPS.idPs]
    private val _idMhs: String? = savedStateHandle[DestinasiDetailPS.idMhs]

    var pembayaranDetailState: DetailPsUiState by mutableStateOf(DetailPsUiState.Loading)
        private set

    init {
        when {
            _idPs != null -> getPembayaranById(_idPs)
            _idMhs != null -> getPembayaranByIdMhs(_idMhs)
            else -> pembayaranDetailState = DetailPsUiState.Error
        }
    }

    fun getPembayaranById(idPs: String) {
        viewModelScope.launch {
            pembayaranDetailState = try {
                val pembayaran = ps.getPembayaranById(idPs.toInt())
                DetailPsUiState.Success(pembayaran)
            } catch (e: IOException) {
                DetailPsUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                DetailPsUiState.Error
            }
        }
    }

    fun getPembayaranByIdMhs(idMhs: String) {
        viewModelScope.launch {
            pembayaranDetailState = try {
                val pembayaran = ps.getPembayaranByIdMhs(idMhs.toInt())
                DetailPsUiState.Success(pembayaran)
            } catch (e: IOException) {
                DetailPsUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                DetailPsUiState.Error
            }
        }
    }
}
