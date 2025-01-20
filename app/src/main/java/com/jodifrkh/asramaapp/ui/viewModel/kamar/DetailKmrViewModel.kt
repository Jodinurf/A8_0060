package com.jodifrkh.asramaapp.ui.viewModel.kamar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.model.Kamar
import com.jodifrkh.asramaapp.data.repository.KamarRepository
import com.jodifrkh.asramaapp.navigation.DestinasiDetailKmr
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailKmrUiState {
    data class Success(val kamar: Kamar) : DetailKmrUiState()
    object Error : DetailKmrUiState()
    object Loading : DetailKmrUiState()
}

class DetailKmrViewModel(
    savedStateHandle: SavedStateHandle,
    private val kmr : KamarRepository
) : ViewModel() {
    private val _idKmr: String = checkNotNull(savedStateHandle[DestinasiDetailKmr.idKmr])

    var kamarDetailState: DetailKmrUiState by mutableStateOf(DetailKmrUiState.Loading)
        private set

    init {
        getKamarById()
    }

    fun getKamarById() {
        viewModelScope.launch {
            kamarDetailState = try {
                val kamar = kmr.getKamarById(_idKmr.toInt())
                DetailKmrUiState.Success(kamar)
            } catch (e: IOException) {
                DetailKmrUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                DetailKmrUiState.Error
            }
        }
    }

}