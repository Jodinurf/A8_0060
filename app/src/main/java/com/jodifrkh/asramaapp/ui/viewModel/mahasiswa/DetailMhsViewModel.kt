package com.jodifrkh.asramaapp.ui.viewModel.mahasiswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jodifrkh.asramaapp.data.model.Mahasiswa
import com.jodifrkh.asramaapp.data.repository.MahasiswaRepository
import com.jodifrkh.asramaapp.navigation.DestinasiDetailMhs
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailMhsUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailMhsUiState()
    object Error : DetailMhsUiState()
    object Loading : DetailMhsUiState()
}

class DetailMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val mhs : MahasiswaRepository
) : ViewModel() {
    private val _idMhs : String = checkNotNull(savedStateHandle[DestinasiDetailMhs.idMhs])

    var mahasiswaDetailState: DetailMhsUiState by mutableStateOf(DetailMhsUiState.Loading)
        private set

    init {
        getMahasiswaById()
    }

    fun getMahasiswaById() {
        viewModelScope.launch {
            mahasiswaDetailState = try {
                val mahasiswa = mhs.getMahasiswaById(_idMhs.toInt())
                DetailMhsUiState.Success(mahasiswa)
            } catch (e: IOException) {
                DetailMhsUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                DetailMhsUiState.Error
            }
        }
    }

}