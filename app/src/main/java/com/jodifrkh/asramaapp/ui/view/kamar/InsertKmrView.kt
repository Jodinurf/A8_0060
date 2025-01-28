package com.jodifrkh.asramaapp.ui.view.kamar

import CustomTopAppBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice.StatusKamarRadioButton
import com.jodifrkh.asramaapp.navigation.DestinasiInsertKmr
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.FormErrorKmrState
import com.jodifrkh.asramaapp.ui.viewModel.kamar.InsertKmrUiEvent
import com.jodifrkh.asramaapp.ui.viewModel.kamar.InsertKmrUiState
import com.jodifrkh.asramaapp.ui.viewModel.kamar.InsertKmrViewModel
import com.jodifrkh.asramaapp.ui.widget.DropdownBangunan
import kotlinx.coroutines.launch

@Composable
fun InsertKmrView(
    onBackClick: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: InsertKmrViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiKmrState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarKmrMessage()
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiInsertKmr.titleRes,
                canNavigateBack = true,
                onBackClick = onBackClick,
                refreshImageRes = R.drawable.ic_bedroom
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
                    InsertBodyKmr(
                        uiState = uiState,
                        formTitle = "Form ${DestinasiInsertKmr.titleRes}",
                        onValueChange = { updatedKmrEvent ->
                            viewModel.updateInsertKmrState(updatedKmrEvent)
                        },
                        onClick = {
                            val isSaved = viewModel.insertKmr()
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
fun InsertBodyKmr(
    modifier: Modifier = Modifier,
    formTitle: String,
    onValueChange: (InsertKmrUiEvent) -> Unit,
    uiState: InsertKmrUiState,
    onClick: suspend () -> Unit,
    isReadOnly: Boolean = false
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

        FormKmrInput(
            insertKmrUiEvent = uiState.insertKmrUiEvent,
            onValueChange = onValueChange,
            errorKmrState = uiState.isKmrEntryValid,
            isReadOnly = isReadOnly,
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
fun FormKmrInput(
    insertKmrUiEvent: InsertKmrUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertKmrUiEvent) -> Unit = {},
    errorKmrState: FormErrorKmrState = FormErrorKmrState(),
    isReadOnly : Boolean = false
) {
    val bangunanOptions = ObjectMultipleChoice.optionsDropdownBangunan()
    var selectedBangunanName by remember {
        mutableStateOf(
            bangunanOptions.find {
                it.second.toString() == insertKmrUiEvent.idKmr
            }?.first.orEmpty()
        )
    }

    LaunchedEffect (insertKmrUiEvent.idKmr) {
        selectedBangunanName = insertKmrUiEvent.idKmr
    }

    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Dropdown Bangunan
        DropdownBangunan(
            selectedName = selectedBangunanName,
            onSelectedNameChanged = { selectedName ->
                selectedBangunanName = selectedName
                val selectedBangunan = bangunanOptions.find { it.first == selectedName }
                selectedBangunan?.let { bangunan ->
                    onValueChange(insertKmrUiEvent.copy(idBgn = bangunan.second.toString()))
                }
            },
            bangunanOptions = bangunanOptions,
            label = "Nama Bangunan",
            isReadOnly = isReadOnly
        )

        if (errorKmrState.idBgn!= null) {
            Text(
                text = errorKmrState.idBgn,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Nomor Kamar
            Column {
                OutlinedTextField(
                    value = insertKmrUiEvent.nomorKmr,
                    onValueChange = {
                        onValueChange(insertKmrUiEvent.copy(nomorKmr = it))
                    },
                    label = { Text("Nomor Kamar") },
                    placeholder = { Text("Masukkan Nomor Kamar") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_door),
                            contentDescription = "Door Icon",
                            tint = Color(0xFFFF6F61),
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorKmrState.nomorKmr != null,
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1DDBAF),
                        unfocusedBorderColor = Color(0xFFB0BEC5),
                        errorBorderColor = Color.Red
                    )
                )

                if (errorKmrState.nomorKmr != null) {
                    Text(
                        text = errorKmrState.nomorKmr,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }
            }
        }

        // Kapasitas
        OutlinedTextField(
            value = insertKmrUiEvent.kapasitas,
            onValueChange = {
                onValueChange(insertKmrUiEvent.copy(kapasitas = it))
            },
            label = { Text("Kapasitas") },
            placeholder = { Text("Masukkan Jumlah Kapasitas") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_capacity),
                    contentDescription = "Ikon Jumlah Lantai",
                    tint = Color(0xFFFF6F61),
                    modifier = Modifier.size(28.dp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = errorKmrState.kapasitas != null,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DDBAF),
                unfocusedBorderColor = Color(0xFFB0BEC5),
                errorBorderColor = Color.Red
            )
        )


        if (errorKmrState.kapasitas != null) {
            Text(
                text = errorKmrState.kapasitas,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        StatusKamarRadioButton(
            statusKamar = insertKmrUiEvent.statusKmr,
            onStatusChanged = {
                onValueChange(insertKmrUiEvent.copy(statusKmr = it))
            },
            error = errorKmrState.statusKmr
        )
    }
}