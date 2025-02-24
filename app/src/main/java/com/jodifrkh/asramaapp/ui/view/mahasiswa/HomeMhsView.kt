package com.jodifrkh.asramaapp.ui.view.mahasiswa

import CustomTopAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice.kamarChoice
import com.jodifrkh.asramaapp.data.model.Mahasiswa
import com.jodifrkh.asramaapp.navigation.DestinasiHomeMhs
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.HomeKmrViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeMhsViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeUiState
import com.jodifrkh.asramaapp.ui.widget.BottomAppBar
import com.jodifrkh.asramaapp.ui.widget.OnError
import com.jodifrkh.asramaapp.ui.widget.OnLoading
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeMhsScreen(
    navigateToItemEntry: () -> Unit,
    onDetailClick: (String) -> Unit = {},
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onClick: (String) -> Unit,
    viewModel: HomeMhsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    kamarViewModel : HomeKmrViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val kamarList = kamarChoice(kamarViewModel)
    LaunchedEffect(Unit) {
        viewModel.getMhs()
        kamarViewModel.getKmr()
    }
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeMhs.titleRes,
                canNavigateBack = false,
                onRefresh = { viewModel.getMhs() },
                onBackClick = onBackClick,
                refreshImageRes = R.drawable.ic_papapas
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.large,
                containerColor = Color(0xFF1DDBAF)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Mahasiswa",
                    tint = Color.White
                )
            }
        },
        bottomBar = {
           BottomAppBar (
               onClick = onClick
           )
        },
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.mhsUIState,
            retryAction = { viewModel.getMhs() },
            modifier = Modifier.padding(innerPadding),
            kamarList = kamarList,
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteMhs(it.idMhs)
                viewModel.getMhs()
            },
            onEditClick = onEditClick
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: StateFlow<HomeUiState>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    kamarList: List<Pair<String, Int>>,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onDetailClick: (String) -> Unit,
    onEditClick: (String) -> Unit
) {
    when (val state = homeUiState.collectAsState().value) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success ->
            if (state.mahasiswa.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Tidak Ada Data Mahasiswa",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                MhsLayout(
                    mahasiswa = state.mahasiswa,
                    kamarList = kamarList,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idMhs.toString()) },
                    onDeleteClick = onDeleteClick,
                    onEditClick = onEditClick
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun MhsLayout(
    mahasiswa: List<Mahasiswa>,
    modifier: Modifier = Modifier,
    onDetailClick: (Mahasiswa) -> Unit,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    kamarList: List<Pair<String, Int>>,
    onEditClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = mahasiswa,
            itemContent = { mahasiswa ->
                val nomorKmr = kamarList.find { it.second == mahasiswa.idKmr }?.first ?: "Kamar tidak ditemukan"
                MhsCard(
                    mahasiswa = mahasiswa,
                    nomorKmr = nomorKmr,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { onDetailClick(mahasiswa) },
                    onDeleteClick = {
                        onDeleteClick(mahasiswa)
                    },
                    onEditClick = {
                        onEditClick(mahasiswa.idMhs.toString())
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MhsCard(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    nomorKmr: String,
    onClick: () -> Unit = {},
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onEditClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(mahasiswa)
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
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = mahasiswa.nama.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = mahasiswa.nama,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "NIM : ${mahasiswa.nim}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Edit Button
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Mahasiswa",
                            tint = Color(0xFFFFC107),
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Delete Button
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus Mahasiswa",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider(color = Color.Gray.copy(alpha = 0.5f))
            Text(
                text = "Nomor Kamar: $nomorKmr",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 12.dp)
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
                Text("Delete Data", color = Color(0xFF2D2D2D))
            }
        },
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel", color = Color(0xFF1DDBAF))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes", color = Color(0xFFFF6F61))
            }
        }
    )
}

