package com.jodifrkh.asramaapp.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jodifrkh.asramaapp.AsramaApplication
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.HomeBgnViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.HomeKmrViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeMhsViewModel
import com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa.HomePSViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeMhsViewModel(AsramaApplication().container.mahasiswaRepository)
        }
        initializer {
            HomeBgnViewModel(AsramaApplication().container.bangunanRepository)
        }
        initializer {
            HomeKmrViewModel(AsramaApplication().container.kamarRepository)
        }
        initializer {
            HomePSViewModel(AsramaApplication().container.pembayaranRepository)
        }
    }
}

fun CreationExtras.AsramaApplication() : AsramaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AsramaApplication)