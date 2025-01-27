package com.jodifrkh.asramaapp.ui.view.pembayaranSewa

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice.StatusBayarRadioButton
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice.optionsDropdownMhs
import com.jodifrkh.asramaapp.navigation.DestinasiInsertPS
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.HomeMhsViewModel
import com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa.FormErrorPSState
import com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa.InsertPSUiEvent
import com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa.InsertPSUiState
import com.jodifrkh.asramaapp.ui.viewModel.pembayaranSewa.InsertPSViewModel
import com.jodifrkh.asramaapp.ui.widget.CustomTopAppBar
import com.jodifrkh.asramaapp.ui.widget.DatePickerField
import kotlinx.coroutines.launch

@Composable
fun InsertPSView(
    idMahasiswa: String,
    onBackClick: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: InsertPSViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiPSState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarPSMessage()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.updateInsertPSState(
            InsertPSUiEvent(
                idMhs = idMahasiswa
            )
        )
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiInsertPS.titleRes,
                canNavigateBack = true,
                onBackClick = onBackClick,
                refreshImageRes = R.drawable.ic_payment
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
                    InsertBodyPS(
                        idMahasiswa = idMahasiswa,
                        uiState = uiState,
                        formTitle = "Form ${DestinasiInsertPS.titleRes}",
                        onValueChange = { updatedPSEvent ->
                            viewModel.updateInsertPSState(updatedPSEvent)
                        },
                        onClick = {
                            val isSaved = viewModel.insertPS()
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
fun InsertBodyPS(
    idMahasiswa: String,
    modifier: Modifier = Modifier,
    formTitle: String,
    onValueChange: (InsertPSUiEvent) -> Unit,
    uiState: InsertPSUiState,
    onClick: suspend () -> Unit,
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

        FormPSInput(
            idMahasiswa = idMahasiswa,
            insertPSUiEvent = uiState.insertPSUiEvent,
            onValueChange = onValueChange,
            errorPSState = uiState.isPSEntryValid,
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
fun FormPSInput(
    insertPSUiEvent: InsertPSUiEvent,
    idMahasiswa: String,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPSUiEvent) -> Unit = {},
    errorPSState: FormErrorPSState = FormErrorPSState(),
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = idMahasiswa,
            onValueChange = {
                onValueChange(insertPSUiEvent.copy(idMhs = it))
            },
            label = { Text("Mahasiswa") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DDBAF),
                unfocusedBorderColor = Color(0xFFB0BEC5)
            )
        )


        if (errorPSState.idMhs != null) {
            Text(
                text = errorPSState.idMhs,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        DatePickerField(
            label = "Tanggal Pembayaran",
            placeholder = "dd-mm-yyyy",
            selectedDate = insertPSUiEvent.tgl,
            onDateChange = { newDate ->
                onValueChange(insertPSUiEvent.copy(tgl = newDate))
            },
            errorMessage = errorPSState.tgl
        )

        OutlinedTextField(
            value = insertPSUiEvent.jumlah,
            onValueChange = {
                onValueChange(insertPSUiEvent.copy(jumlah = it))
            },
            label = { Text("Jumlah Pembayaran") },
            placeholder = { Text("Masukkan Jumlah Pembayaran") },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.ic_money),
                    contentDescription = "Ikon Jumlah Pembayaran",
                    modifier = Modifier.size(28.dp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = errorPSState.jumlah != null,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DDBAF),
                unfocusedBorderColor = Color(0xFFB0BEC5),
                errorBorderColor = Color.Red
            )
        )

        if (errorPSState.jumlah != null) {
            Text(
                text = errorPSState.jumlah,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        StatusBayarRadioButton(
            statusBayar = insertPSUiEvent.status,
            onStatusChanged = {
                onValueChange(insertPSUiEvent.copy(status = it))
            },
            error = errorPSState.status
        )
    }
}

