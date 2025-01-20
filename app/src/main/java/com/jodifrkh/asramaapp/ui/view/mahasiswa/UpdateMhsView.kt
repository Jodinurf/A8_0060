package com.jodifrkh.asramaapp.ui.view.mahasiswa

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.navigation.DestinasiUpdateMhs
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.UpdateMhsViewModel
import com.jodifrkh.asramaapp.ui.widget.CustomTopAppBar
import kotlinx.coroutines.delay

@Composable
fun UpdateMhsScreen(
    onClickBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: UpdateMhsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState = viewModel.updateMhsUiState

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.resetSnackBarMhsMessage()
        }
    }

    Scaffold (
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateMhs.titleRes,
                canNavigateBack = true,
                onBackClick = onClickBack,
                refreshImageRes = R.drawable.ic_student,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(visible = true) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5),
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    InsertBodyMhs(
                        uiState = uiState,
                        formTitle = "Form ${DestinasiUpdateMhs.titleRes}",
                        onValueChange = { updatedMhsEvent ->
                            viewModel.updateInsertMhsState(updatedMhsEvent)
                        },
                        onClick = {
                            val isSaved = viewModel.updateMhs()
                            if (isSaved) {
                                delay(1000)
                                onNavigate()
                            }
                        },
                        isReadOnly = true,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}