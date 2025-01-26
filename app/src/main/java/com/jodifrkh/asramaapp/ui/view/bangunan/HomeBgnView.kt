package com.jodifrkh.asramaapp.ui.view.bangunan

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    onEditClick: (String) -> Unit,
    onDropdownClick: (String) -> Unit,
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
                canNavigateBack = false,
                onRefresh = {
                    viewModel.getBgn()
                },
                onBackClick = onBackClick,
                refreshImageRes = R.drawable.icon_building,
                onDropdownClick = onDropdownClick

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
    onDeleteClick: (Bangunan) -> Unit,
    onDetailClick: (String) -> Unit,
    onEditClick: (String) -> Unit
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
                    onDeleteClick = onDeleteClick,
                    onEditClick = onEditClick
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
    onDeleteClick: (Bangunan) -> Unit = {},
    onEditClick: (String) -> Unit
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
                onDeleteClick = onDeleteClick,
                onEditClick = { onEditClick(bangunan.idBgn.toString())}
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
    onDeleteClick: (Bangunan) -> Unit = {},
    onEditClick: () -> Unit = {}
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
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5),
            contentColor = Color(0xFF212121)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_building),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = bangunan.namaBgn,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0xFFFFC107)
                    )
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Divider(color = Color.Gray.copy(alpha = 0.5f))

            Text(
                text = "Alamat: ${bangunan.alamat}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )


        }
    }
}

@Composable
fun DeleteConfirmationDialog(
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
                Text("Hapus Data", color = Color(0xFF2D2D2D))
            }
        },
        text = {
            Text(
                text = "Apakah Anda yakin ingin menghapus data ini?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Batal", color = Color(0xFF1DDBAF))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Ya", color = Color(0xFFFF6F61))
            }
        }
    )
}
