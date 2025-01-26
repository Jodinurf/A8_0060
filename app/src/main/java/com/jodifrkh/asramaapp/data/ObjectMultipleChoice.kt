package com.jodifrkh.asramaapp.data


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.HomeBgnViewModel
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.HomeUiState
import com.jodifrkh.asramaapp.ui.viewModel.kamar.HomeKmrViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeMhsViewModel

object ObjectMultipleChoice {
    @Composable
    fun optionsDropdownBangunan(
        bangunanHomeViewModel: HomeBgnViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<String, Int>> {
        val dataBangunan by bangunanHomeViewModel.bgnUIState.collectAsState()

        return if (dataBangunan is HomeUiState.Success) {
            val bangunanList = (dataBangunan as HomeUiState.Success).bangunan
            bangunanList.map { it.namaBgn to it.idBgn }
        } else {
            emptyList()
        }
    }

    @Composable
    fun optionsDropDownKamar(
        kamarHomeViewModel : HomeKmrViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ) : List<Pair<String, Int>> {
        val dataKamar by kamarHomeViewModel.kmrUIState.collectAsState()

        return if (dataKamar is com.jodifrkh.asramaapp.ui.viewModel.kamar.HomeUiState.Success) {
            val kamarList = (dataKamar as com.jodifrkh.asramaapp.ui.viewModel.kamar.HomeUiState.Success).kamar
            kamarList.map { it.nomorKmr to it.idKmr }
        } else {
            emptyList()
        }
    }

    @Composable
    fun optionsDropdownMhs(
        homeMhsViewModel: HomeMhsViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<String, Int>> {
        val dataMahasiswa by homeMhsViewModel.mhsUIState.collectAsState()

        return if (dataMahasiswa is com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeUiState.Success) {
            val namaList = (dataMahasiswa as com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeUiState.Success).mahasiswa
            namaList.map { it.nama to it.idMhs }
        } else {
            emptyList()
        }
    }


    @Composable
    fun StatusKamarRadioButton(
        statusKamar: String,
        onStatusChanged: (String) -> Unit,
        error: String? = null,
        modifier: Modifier = Modifier
    ) {
        val focusedColor = Color(0xFF1DDBAF)
        val unfocusedColor = Color(0xFFB0BEC5)
        val errorColor = Color.Red

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Status Kamar",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val options = listOf("Terisi", "Kosong")
                options.forEach { option ->
                    val isSelected = statusKamar == option

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { onStatusChanged(option) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (error == null) focusedColor else errorColor,
                                unselectedColor = unfocusedColor,
                            )
                        )
                        Text(
                            text = option,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Show error if exists
            if (error != null) {
                Text(
                    text = error,
                    color = errorColor,
                    fontSize = 12.sp
                )
            }
        }
    }

    @Composable
    fun StatusBayarRadioButton(
        statusBayar: String,
        onStatusChanged: (String) -> Unit,
        error: String? = null,
        modifier: Modifier = Modifier
    ) {
        val focusedColor = Color(0xFF1DDBAF)
        val unfocusedColor = Color(0xFFB0BEC5)
        val errorColor = Color.Red

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Status Pembayaran",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val options = listOf("Belum Lunas", "Lunas")
                options.forEach { option ->
                    val isSelected = statusBayar == option

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { onStatusChanged(option) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (error == null) focusedColor else errorColor,
                                unselectedColor = unfocusedColor,
                            )
                        )
                        Text(
                            text = option,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Show error if exists
            if (error != null) {
                Text(
                    text = error,
                    color = errorColor,
                    fontSize = 12.sp
                )
            }
        }
    }

}
