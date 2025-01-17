package com.jodifrkh.asramaapp.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jodifrkh.asramaapp.AsramaApplication
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(AsramaApplication().container.mahasiswaRepository)
        }
    }
}

fun CreationExtras.AsramaApplication() : AsramaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AsramaApplication)