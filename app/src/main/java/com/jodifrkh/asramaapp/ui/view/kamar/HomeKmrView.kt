package com.jodifrkh.asramaapp.ui.view.kamar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.model.Kamar
import com.jodifrkh.asramaapp.navigation.DestinasiHomeKmr
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.HomeUiState
import com.jodifrkh.asramaapp.ui.viewModel.kamar.HomeKmrViewModel
import com.jodifrkh.asramaapp.ui.widget.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKmrScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeKmrViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeKmr.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKmr()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Mahasiswa")
            }
        },
    ){innerPadding ->
        HomeStatus(
            homeUiState = viewModel.kmrUIState,
            retryAction = {viewModel.getKmr()},
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deleteKmr(it.idKmr)
                viewModel.getKmr()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kamar) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when(homeUiState){
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success ->
            if(homeUiState.kamar.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak Ada Data Kamar")
                }
            } else {
                BgnLayout(
                    kamar = homeUiState.kamar, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = "")
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun BgnLayout(
    kamar: List<Kamar>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kamar) -> Unit,
    onDeleteClick: (Kamar) -> Unit = {}
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(kamar) { kamar ->
            BgnCard(
                kamar = kamar,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {onDetailClick(kamar)},
                onDeleteClick = {
                    onDeleteClick(kamar)
                }
            )
        }
    }
}


@Composable
fun BgnCard(
    kamar: Kamar,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kamar) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(kamar)
            },
            onDeleteCancel = { showDialog = false }
        )
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = kamar.nomorKmr,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text(
                    text = kamar.idKmr.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}
