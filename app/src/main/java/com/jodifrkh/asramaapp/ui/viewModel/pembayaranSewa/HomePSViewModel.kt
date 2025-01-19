package com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.model.Pembayaran
import com.jodifrkh.asramaapp.data.repository.PembayaranRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val Pembayaran: List<Pembayaran>) : HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomePSViewModel (private val ps: PembayaranRepository): ViewModel(){
    var psUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    init {
        getPs()
    }
    fun getPs(){
        viewModelScope.launch {
            psUIState = HomeUiState.Loading
            psUIState = try {
                HomeUiState.Success(ps.getPembayaran().data)
            } catch (e: IOException){
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
    fun deletePs(idPs: Int) {
        viewModelScope.launch{
            try {
                ps.deletePembayaran(idPs)
            } catch (e: IOException){
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}