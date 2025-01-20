package com.jodifrkh.asramaapp.ui.viewModel.mahasiswa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.model.Mahasiswa
import com.jodifrkh.asramaapp.data.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeMhsViewModel (private val mhs: MahasiswaRepository): ViewModel(){

    private val _mhsUIState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    val mhsUIState : StateFlow<HomeUiState> get() = _mhsUIState

    init {
        getMhs()
    }
    fun getMhs(){
        viewModelScope.launch {
            _mhsUIState.value = HomeUiState.Loading
            _mhsUIState.value = try {
                HomeUiState.Success(mhs.getMahasiswa().data)
            } catch (e: IOException){
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
    fun deleteMhs(idMhs: Int) {
        viewModelScope.launch{
            try {
                mhs.deleteMahasiswa(idMhs)
            } catch (e: IOException){
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}