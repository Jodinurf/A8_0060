package com.jodifrkh.asramaapp.ui.view.kamar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice.StatusKamarRadioButton
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice.optionsDropdownBangunan
import com.jodifrkh.asramaapp.navigation.DestinasiUpdateKmr
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.HomeBgnViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.FormErrorKmrState
import com.jodifrkh.asramaapp.ui.viewModel.kamar.InsertKmrUiEvent
import com.jodifrkh.asramaapp.ui.viewModel.kamar.InsertKmrUiState
import com.jodifrkh.asramaapp.ui.viewModel.kamar.UpdateKmrViewModel
import com.jodifrkh.asramaapp.ui.widget.TopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UpdateKmrScreen(
    onClickBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: UpdateKmrViewModel = viewModel(factory = PenyediaViewModel.Factory),
    bgnViewModel : HomeBgnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState = viewModel.updateKmrUiState
    val bgnList = optionsDropdownBangunan(bgnViewModel)

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.resetSnackBarKmrMessage()
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = DestinasiUpdateKmr.titleRes,
                canNavigateBack = true,
                onBackClick = onClickBack,
                refreshImageRes = R.drawable.ic_bedroom,
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
                    InsertUpdateBodyKmr(
                        uiState = uiState,
                        formTitle = "Form ${DestinasiUpdateKmr.titleRes}",
                        onValueChange = { updatedKmrEvent ->
                            viewModel.updateInsertKmrState(updatedKmrEvent)
                        },
                        onClick = {
                            val isSaved = viewModel.updateKmr()
                            if (isSaved) {
                                delay(1000)
                                onNavigate()
                            }
                        },
                        isReadOnly = true,
                        bgnList = bgnList,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun InsertUpdateBodyKmr(
    modifier: Modifier = Modifier,
    formTitle: String,
    onValueChange: (InsertKmrUiEvent) -> Unit,
    uiState: InsertKmrUiState,
    onClick: suspend () -> Unit,
    isReadOnly: Boolean = false,
    bgnList : List<Pair<String, Int>>
) {
    val coroutineScope = rememberCoroutineScope()

    val bangunan = uiState.insertKmrUiEvent
    val idBgnKmr = bangunan.idBgn

    val namaBgn = bgnList.find { it.second.toString() == idBgnKmr }?.first
        ?: "Bangunan tidak ditemukan"

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

        FormKmrInputUpdate(
            insertKmrUiEvent = uiState.insertKmrUiEvent,
            onValueChange = onValueChange,
            errorKmrState = uiState.isKmrEntryValid,
            isReadOnly = isReadOnly,
            modifier = Modifier.fillMaxWidth(),
            namaBgn = namaBgn
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
fun FormKmrInputUpdate(
    insertKmrUiEvent: InsertKmrUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertKmrUiEvent) -> Unit = {},
    errorKmrState: FormErrorKmrState = FormErrorKmrState(),
    isReadOnly : Boolean = false,
    namaBgn : String
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
        // Tampilan Nama Mahasiswa yang Diperbarui
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikon Mahasiswa
            Image(
                painter = painterResource(R.drawable.ic_building),
                contentDescription = "Building Icon",
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFDFF7F0), shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Informasi Nama Mahasiswa
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Nama Bangunan",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF757575)
                )
                Text(
                    text = namaBgn,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
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