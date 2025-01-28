package com.jodifrkh.asramaapp.ui.view.pembayaranSewa

import CustomTopAppBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice.optionsDropdownMhs
import com.jodifrkh.asramaapp.data.model.Pembayaran
import com.jodifrkh.asramaapp.navigation.DestinasiHomePS
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeMhsViewModel
import com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa.HomePSViewModel
import com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa.HomeUiState
import com.jodifrkh.asramaapp.ui.widget.BottomAppBar
import com.jodifrkh.asramaapp.ui.widget.OnError
import com.jodifrkh.asramaapp.ui.widget.OnLoading

@Composable
fun HomePsScreen(
    onDetailClick: (String) -> Unit = {},
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onClick: (String) -> Unit,
    viewModel: HomePSViewModel = viewModel(factory = PenyediaViewModel.Factory),
    mhsViewModel: HomeMhsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val mhsList = optionsDropdownMhs(mhsViewModel)
    LaunchedEffect(Unit) {
        viewModel.getPs()
        mhsViewModel.getMhs()
    }
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomePS.titleRes,
                canNavigateBack = false,
                onRefresh = { viewModel.getPs() },
                onBackClick = onBackClick,
                refreshImageRes = R.drawable.ic_papapas,
            )
        },
        bottomBar = {
            BottomAppBar(
                onClick = onClick,
            )
        },
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.psUIState,
            retryAction = { viewModel.getPs() },
            modifier = Modifier.padding(innerPadding),
            mhsList = mhsList,
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePs(it.idPs)
                viewModel.getPs()
            },
            onEditClick = onEditClick
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    mhsList: List<Pair<String, Int>>,
    onEditClick: (String) -> Unit,
    onDeleteClick: (Pembayaran) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success ->
            if (homeUiState.Pembayaran.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak Ada Data Pembayaran", style = MaterialTheme.typography.titleMedium)
                }
            } else {
                PsLayout(
                    pembayaran = homeUiState.Pembayaran,
                    mhsList = mhsList,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = onDetailClick,
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = onEditClick
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PsLayout(
    pembayaran: List<Pembayaran>,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Pembayaran) -> Unit = {},
    onEditClick: (String) -> Unit,
    mhsList: List<Pair<String, Int>>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = pembayaran,
            itemContent = { pembayaran ->
                val nama = mhsList.find { it.second == pembayaran.idMhs }?.first ?: "Mahasiswa tidak ditemukan"
                PsCard(
                    pembayaran = pembayaran,
                    nama = nama,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { onDetailClick(pembayaran.idPs.toString()) },
                    onDeleteClick = {
                        onDeleteClick(pembayaran)
                    },
                    onEditClick = {
                        onEditClick(pembayaran.idPs.toString())
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PsCard(
    modifier: Modifier = Modifier,
    pembayaran: Pembayaran,
    nama: String,
    onClick: () -> Unit = {},
    onDeleteClick: (Pembayaran) -> Unit = {},
    onEditClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(pembayaran)
            },
            onDeleteCancel = { showDialog = false }
        )
    }

    Card(
        modifier = modifier.padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5),
            contentColor = Color(0xFF212121)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_payment),
                    contentDescription = null,
                    modifier = Modifier.size(56.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Nama dan Tanggal
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = nama,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tanggal: ${pembayaran.tgl}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Tombol Edit dan Delete
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Transaksi",
                            tint = Color(0xFFFFC107)
                        )
                    }
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus Transaksi",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider(color = Color.Gray.copy(alpha = 0.5f))
            Text(
                text = "Status: ${pembayaran.status}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}




@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(0xFFFF6F61),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Hapus Data", style = MaterialTheme.typography.titleMedium)
            }
        },
        text = {
            Text("Apakah Anda yakin ingin menghapus data ini?", style = MaterialTheme.typography.bodyMedium)
        },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Batal", color = MaterialTheme.colorScheme.primary)
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Hapus", color = MaterialTheme.colorScheme.error)
            }
        }
    )
}
