package com.jodifrkh.asramaapp.ui.viewModel.bangunan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.model.Bangunan
import com.jodifrkh.asramaapp.data.repository.BangunanRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val bangunan: List<Bangunan>) : HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeBgnViewModel (private val bgn: BangunanRepository): ViewModel(){
    var bgnUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    init {
        getBgn()
    }
    fun getBgn(){
        viewModelScope.launch {
            bgnUIState = HomeUiState.Loading
            bgnUIState = try {
                HomeUiState.Success(bgn.getBangunan().data)
            } catch (e: IOException){
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
    fun deleteBgn(idBgn: Int) {
        viewModelScope.launch{
            try {
                bgn.deleteBangunan(idBgn)
            } catch (e: IOException){
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}