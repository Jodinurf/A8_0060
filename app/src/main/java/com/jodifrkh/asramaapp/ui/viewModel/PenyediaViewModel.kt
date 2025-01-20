package com.jodifrkh.asramaapp.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jodifrkh.asramaapp.AsramaApplication
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.DetailBgnViewModel
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.HomeBgnViewModel
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.InsertBgnViewModel
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.UpdateBgnViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.DetailKmrViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.HomeKmrViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.InsertKmrViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.UpdateKmrViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.DetailMhsViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeMhsViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.InsertMhsViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.UpdateMhsViewModel
import com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa.HomePSViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // ------------------Bangunan------------------- //
        initializer {
            HomeBgnViewModel(AsramaApplication().container.bangunanRepository)
        }
        initializer {
            InsertBgnViewModel(AsramaApplication().container.bangunanRepository)
        }
        initializer {
            DetailBgnViewModel(
                createSavedStateHandle(),
                AsramaApplication().container.bangunanRepository
            )
        }
        initializer {
            UpdateBgnViewModel(
                createSavedStateHandle(),
                AsramaApplication().container.bangunanRepository
            )
        }
        // ------------------Kamar------------------- //
        initializer {
            HomeKmrViewModel(AsramaApplication().container.kamarRepository)
        }
        initializer {
            InsertKmrViewModel(AsramaApplication().container.kamarRepository)
        }
        initializer {
            DetailKmrViewModel(
                createSavedStateHandle(),
                AsramaApplication().container.kamarRepository
            )
        }
        initializer {
            UpdateKmrViewModel(
                createSavedStateHandle(),
                AsramaApplication().container.kamarRepository
            )
        }
        // ------------------Mahasiswa------------------- //
        initializer {
            HomeMhsViewModel(AsramaApplication().container.mahasiswaRepository)
        }
        initializer {
            InsertMhsViewModel(AsramaApplication().container.mahasiswaRepository)
        }
        initializer {
            DetailMhsViewModel(
                createSavedStateHandle(),
                AsramaApplication().container.mahasiswaRepository
            )
        }
        initializer {
            UpdateMhsViewModel(
                createSavedStateHandle(),
                AsramaApplication().container.mahasiswaRepository
            )
        }

        // ------------------Pembayaran Sewa------------------- //
        initializer {
            HomePSViewModel(AsramaApplication().container.pembayaranRepository)
        }
    }
}

fun CreationExtras.AsramaApplication() : AsramaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AsramaApplication)