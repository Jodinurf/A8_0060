package com.jodifrkh.asramaapp.ui.viewModel.bangunan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.model.Bangunan
import com.jodifrkh.asramaapp.data.repository.BangunanRepository
import com.jodifrkh.asramaapp.navigation.DestinasiDetailBgn
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailBgnUiState {
    data class Success(val bangunan: Bangunan) : DetailBgnUiState()
    object Error : DetailBgnUiState()
    object Loading : DetailBgnUiState()
}

class DetailBgnViewModel(
    savedStateHandle: SavedStateHandle,
    private val bgn : BangunanRepository
) : ViewModel() {
    private val _idBgn: String = checkNotNull(savedStateHandle[DestinasiDetailBgn.idBgn])

    var bangunanDetailState: DetailBgnUiState by mutableStateOf(DetailBgnUiState.Loading)
        private set

    init {
        getBangunanById()
    }

    fun getBangunanById() {
        viewModelScope.launch {
            bangunanDetailState = try {
                val bangunan = bgn.getBangunanById(_idBgn.toInt())
                DetailBgnUiState.Success(bangunan)
            } catch (e: IOException) {
                DetailBgnUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                DetailBgnUiState.Error
            }
        }
    }

}