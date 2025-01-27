package com.jodifrkh.asramaapp.ui.view.pembayaranSewa

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice.optionsDropdownMhs
import com.jodifrkh.asramaapp.data.model.Pembayaran
import com.jodifrkh.asramaapp.navigation.DestinasiDetailPS
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeMhsViewModel
import com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa.DetailPsUiState
import com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa.DetailPsViewModel
import com.jodifrkh.asramaapp.ui.widget.CustomTopAppBar
import com.jodifrkh.asramaapp.ui.widget.OnError
import com.jodifrkh.asramaapp.ui.widget.OnLoading

@Composable
fun DetailPsScreen(
    onClickBack: () -> Unit,
    idPs: String? = null,
    idMhs: String? = null,
    onUpdateClick: () -> Unit,
    viewModel: DetailPsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    mhsViewModel : HomeMhsViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    val mhsList = optionsDropdownMhs(mhsViewModel)

    LaunchedEffect(idPs, idMhs) {
        when {
            idPs != null -> viewModel.getPembayaranById(idPs)
            idMhs != null -> viewModel.getPembayaranByIdMhs(idMhs)
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailPS.titleRes,
                canNavigateBack = true,
                onBackClick = onClickBack,
                refreshImageRes = R.drawable.ic_payment,
                onRefresh = {
                    when {
                        idPs != null -> viewModel.getPembayaranById(idPs)
                        idMhs != null -> viewModel.getPembayaranByIdMhs(idMhs)
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onUpdateClick,
                shape = MaterialTheme.shapes.large,
                containerColor = Color(0xFF1DDBAF)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pembayaran",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        DetailPsStatus(
            modifier = Modifier.padding(innerPadding),
            detailPsUiState = viewModel.pembayaranDetailState,
            retryAction = {
                when {
                    idPs != null -> viewModel.getPembayaranById(idPs)
                    idMhs != null -> viewModel.getPembayaranByIdMhs(idMhs)
                }
            },
            mhsList = mhsList,
        )
    }
}

@Composable
fun DetailPsStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailPsUiState: DetailPsUiState,
    mhsList: List<Pair<String, Int>>
) {
    when (detailPsUiState) {
        is DetailPsUiState.Loading -> OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is DetailPsUiState.Success -> {
            val pembayaran = detailPsUiState.pembayaran
            if (pembayaran.idPs.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Data Pembayaran tidak ditemukan",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                val idPsMhs = pembayaran.idMhs

                val nama = mhsList.find { it.second == idPsMhs }?.first
                    ?: "Mahasiswa tidak ditemukan"

                AnimatedVisibility(visible = true) {
                    ItemDetailPs(
                        pembayaran = pembayaran,
                        nama = nama,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                    )
                }
            }
        }

        is DetailPsUiState.Error -> OnError(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailPs(
    modifier: Modifier = Modifier,
    pembayaran: Pembayaran,
    nama: String,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5),
            contentColor = Color(0xFF212121)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Detail Pembayaran",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1DDBAF),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Nama Mahasiswa
            ComponentDetailPs(
                title = "Nama Mahasiswa",
                content = nama,
                icon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Ikon Nama Mahasiswa",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()

            // Jumlah Pembayaran
            ComponentDetailPs(
                title = "Jumlah Pembayaran",
                content = "Rp.${pembayaran.jumlah}",
                icon = {
                    Image(
                        painter = painterResource(R.drawable.ic_money),
                        contentDescription = "Ikon Jumlah Pembayaran",
                        Modifier.size(28.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()


            ComponentDetailPs(
                title = "Tanggal Pembayaran",
                content = pembayaran.tgl,
                icon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Ikon Tanggal Pembayaran",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()

            // Status Pembayaran
            ComponentDetailPs(
                title = "Status Pembayaran",
                content = pembayaran.status,
                icon = {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Ikon Status Pembayaran",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )
        }
    }
}


@Composable
fun ComponentDetailPs(
    title: String,
    content: String,
    icon: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.surfaceTint
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
