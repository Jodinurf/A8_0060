package com.jodifrkh.asramaapp.ui.view.bangunan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.model.Bangunan
import com.jodifrkh.asramaapp.navigation.DestinasiHomeBgn
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.HomeBgnViewModel
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.HomeUiState
import com.jodifrkh.asramaapp.ui.widget.CustomTopAppBar
import com.jodifrkh.asramaapp.ui.widget.OnError
import com.jodifrkh.asramaapp.ui.widget.OnLoading
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeBgnScreen(
    navigateToItemEntry: () -> Unit,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeBgnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBackClick: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getBgn()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeBgn.titleRes,
                canNavigateBack = true,
                onRefresh = {
                    viewModel.getBgn()
                },
                onBackClick = onBackClick,
                refreshImageRes = R.drawable.icon_building
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
                    contentDescription = "Add Bangunan",
                    tint = Color.White
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.bgnUIState,
            retryAction = { viewModel.getBgn() },
            modifier = Modifier
                .padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { bangunan ->
                viewModel.deleteBgn(bangunan.idBgn)
            }
        )
    }
}


@Composable
fun HomeStatus(
    homeUiState: StateFlow<HomeUiState>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Bangunan) -> Unit,
    onDetailClick: (String) -> Unit
) {
    when (val state = homeUiState.collectAsState().value) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (state.bangunan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Tidak Ada Data Bangunan",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                BgnLayout(
                    bangunan = state.bangunan,
                    modifier = modifier.fillMaxWidth(),
                    onClick = onDetailClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun BgnLayout(
    bangunan: List<Bangunan>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    onDeleteClick: (Bangunan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(bangunan) { bangunan ->
            BgnCard(
                bangunan = bangunan,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onClick(bangunan.idBgn.toString())},
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BgnCard(
    bangunan: Bangunan,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    onDeleteClick: (Bangunan) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(bangunan)
            },
            onDeleteCancel = { showDialog = false }
        )
    }

    Card(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5),
            contentColor = Color(0xFF212121)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_building),
                    contentDescription = null,
                    modifier = Modifier.size(58.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = bangunan.namaBgn,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Divider()

            Text(
                text = bangunan.alamat,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
