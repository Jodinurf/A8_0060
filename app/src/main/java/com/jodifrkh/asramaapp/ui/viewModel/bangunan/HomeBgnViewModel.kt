package com.jodifrkh.asramaapp.ui.viewModel.bangunan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.model.Bangunan
import com.jodifrkh.asramaapp.data.repository.BangunanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState {
    data class Success(val bangunan: List<Bangunan>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}


class HomeBgnViewModel(private val repository: BangunanRepository) : ViewModel() {

    private val _bgnUIState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val bgnUIState: StateFlow<HomeUiState> get() = _bgnUIState

    init {
        getBgn()
    }

    fun getBgn() {
        viewModelScope.launch {
            _bgnUIState.value = HomeUiState.Loading
            _bgnUIState.value = try {
                val bangunanList = repository.getBangunan().data
                HomeUiState.Success(bangunanList)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteBgn(idBgn: Int) {
        viewModelScope.launch {
            try {
                repository.deleteBangunan(idBgn)
                getBgn()
            } catch (e: IOException) {
                _bgnUIState.value = HomeUiState.Error
            } catch (e: HttpException) {
                _bgnUIState.value = HomeUiState.Error
            }
        }
    }
}

