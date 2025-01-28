package com.jodifrkh.asramaapp.ui.view.bangunan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.navigation.DestinasiInsertBgn
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.FormErrorBgnState
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.InsertBgnUiEvent
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.InsertBgnUiState
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.InsertBgnViewModel
import com.jodifrkh.asramaapp.ui.widget.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun InsertBgnView(
    onBackClick: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: InsertBgnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiBgnState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarBgnMessage()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiInsertBgn.titleRes,
                canNavigateBack = true,
                onBackClick = onBackClick,
                refreshImageRes = R.drawable.icon_building
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
                    InsertBodyBgn(
                        uiState = uiState,
                        formTitle = "Form ${DestinasiInsertBgn.titleRes}",
                        onValueChange = { updatedBgnEvent ->
                            viewModel.updateInsertBgnState(updatedBgnEvent)
                        },
                        onClick = {
                            val isSaved = viewModel.insertBgn()
                            if (isSaved) {
                                onNavigate()
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun InsertBodyBgn(
    modifier: Modifier = Modifier,
    formTitle: String,
    onValueChange: (InsertBgnUiEvent) -> Unit,
    uiState: InsertBgnUiState,
    onClick: suspend () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formTitle,
            color = Color(0xFF1DDBAF),
            style = MaterialTheme.typography.headlineMedium
        )

        FormBgnInput(
            insertBgnUiEvent = uiState.insertBgnUiEvent,
            onValueChange = onValueChange,
            errorBgnState = uiState.isBgnEntryValid,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    onClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1DDBAF),
                contentColor = Color.White
            )
        ) {
            Text(text = "Simpan", fontSize = 18.sp)
        }
    }
}


@Composable
fun FormBgnInput(
    insertBgnUiEvent: InsertBgnUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertBgnUiEvent) -> Unit = {},
    errorBgnState: FormErrorBgnState = FormErrorBgnState()
) {
    Column (
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Nama bangunan
        OutlinedTextField(
            value = insertBgnUiEvent.namaBgn,
            onValueChange = {
                onValueChange(insertBgnUiEvent.copy(namaBgn = it))
            },
            label = { Text("Nama Bangunan") },
            placeholder = { Text("Masukkan nama Bangunan") },
            leadingIcon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Bangunan Icon",
                    tint = Color(0xFFFF6F61) // Secondary color
                )
            },
            modifier = Modifier.fillMaxWidth(),
            isError = errorBgnState.namaBgn != null,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DDBAF), // Focused border color
                unfocusedBorderColor = Color(0xFFB0BEC5), // Light border color
                errorBorderColor = Color.Red
            )
        )
        if (errorBgnState.namaBgn != null) {
            Text(
                text = errorBgnState.namaBgn,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        // Alamat
        OutlinedTextField(
            value = insertBgnUiEvent.alamat,
            onValueChange = {
                onValueChange(insertBgnUiEvent.copy(alamat = it))
            },
            label = { Text("Alamat") },
            placeholder = { Text("Masukkan Alamat") },
            leadingIcon = {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Alamat Icon",
                    tint = Color(0xFFFF6F61) // Secondary color
                )
            },
            modifier = Modifier.fillMaxWidth(),
            isError = errorBgnState.alamat != null,
            singleLine = false,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DDBAF), // Focused border color
                unfocusedBorderColor = Color(0xFFB0BEC5), // Light border color
                errorBorderColor = Color.Red
            )
        )
        if (errorBgnState.alamat != null) {
            Text(
                text = errorBgnState.alamat,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        // Jumlah lantai
        OutlinedTextField(
            value = insertBgnUiEvent.jmlhLantai,
            onValueChange = {
                onValueChange(insertBgnUiEvent.copy(jmlhLantai = it))
            },
            label = { Text("Jumlah Lantai") },
            placeholder = { Text("Masukkan Jumlah Lantai") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.stairs_icon),
                    contentDescription = "Ikon Jumlah Lantai",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(28.dp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = errorBgnState.jmlhLantai != null,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DDBAF),
                unfocusedBorderColor = Color(0xFFB0BEC5),
                errorBorderColor = Color.Red
            )
        )
        if (errorBgnState.jmlhLantai != null) {
            Text(
                text = errorBgnState.jmlhLantai,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
    }
}
