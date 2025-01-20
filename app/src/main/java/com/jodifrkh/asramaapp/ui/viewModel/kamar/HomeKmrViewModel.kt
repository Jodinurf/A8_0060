package com.jodifrkh.asramaapp.ui.viewModel.kamar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.model.Kamar
import com.jodifrkh.asramaapp.data.repository.KamarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val kamar: List<Kamar>) : HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeKmrViewModel (private val kmr: KamarRepository): ViewModel(){

    private val _kmrUIState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    val kmrUIState: StateFlow<HomeUiState> get() = _kmrUIState

    init {
        getKmr()
    }
    fun getKmr(){
        viewModelScope.launch {
            _kmrUIState.value = HomeUiState.Loading
            _kmrUIState.value = try {
                val kamarList = kmr.getKamar().data
                HomeUiState.Success(kamarList)
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