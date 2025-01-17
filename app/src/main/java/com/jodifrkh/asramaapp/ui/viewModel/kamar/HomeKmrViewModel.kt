package com.jodifrkh.asramaapp.ui.viewModel.kamar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.model.Kamar
import com.jodifrkh.asramaapp.data.repository.KamarRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val kamar: List<Kamar>) : HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeKmrViewModel (private val kmr: KamarRepository): ViewModel(){
    var kmrUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    init {
        getKmr()
    }
    fun getKmr(){
        viewModelScope.launch {
            kmrUIState = HomeUiState.Loading
            kmrUIState = try {
                HomeUiState.Success(kmr.getKamar().data)
            } catch (e: IOException){
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
    fun deleteKmr(idkmr: Int) {
        viewModelScope.launch{
            try {
                kmr.deleteKamar(idkmr)
            } catch (e: IOException){
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}