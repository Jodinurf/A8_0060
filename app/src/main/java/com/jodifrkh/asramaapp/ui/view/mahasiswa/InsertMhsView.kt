package com.jodifrkh.asramaapp.ui.view.mahasiswa

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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice
import com.jodifrkh.asramaapp.navigation.DestinasiInsertMhs
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.FormErrorMhsState
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.InsertMhsUiEvent
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.InsertMhsUiState
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.InsertMhsViewModel
import com.jodifrkh.asramaapp.ui.widget.CustomTopAppBar
import com.jodifrkh.asramaapp.ui.widget.DropdownKamar
import kotlinx.coroutines.launch

@Composable
fun InsertMhsView(
    onBackClick: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: InsertMhsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiMhsState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMhsMessage()
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiInsertMhs.titleRes,
                canNavigateBack = true,
                onBackClick = onBackClick,
                refreshImageRes = R.drawable.ic_student
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
                        formTitle = "Form ${DestinasiInsertMhs.titleRes}",
                        onValueChange = { updatedMhsEvent ->
                            viewModel.updateInsertMhsState(updatedMhsEvent)
                        },
                        onClick = {
                            val isSaved = viewModel.insertMhs()
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
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    formTitle: String,
    onValueChange: (InsertMhsUiEvent) -> Unit,
    uiState: InsertMhsUiState,
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

        FormMhsInput(
            insertMhsUiEvent = uiState.insertMhsUiEvent,
            onValueChange = onValueChange,
            errorMhsState = uiState.isMhsEntryValid,
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
fun FormMhsInput(
    insertMhsUiEvent: InsertMhsUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertMhsUiEvent) -> Unit = {},
    errorMhsState: FormErrorMhsState = FormErrorMhsState(),
    isReadOnly : Boolean = false
) {
    val kamarOptions = ObjectMultipleChoice.optionsDropDownKamar()
    var selectedNoKamar by remember {
        mutableStateOf(
            kamarOptions.find { it.second.toString() == insertMhsUiEvent.idKmr  }?.first.orEmpty()
        )
    }

    LaunchedEffect(insertMhsUiEvent.idKmr) {
        selectedNoKamar = insertMhsUiEvent.idKmr
    }

    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = insertMhsUiEvent.nama,
            onValueChange = {
                onValueChange(insertMhsUiEvent.copy(nama = it))
            },
            label = { Text("Nama Mahasiswa") },
            placeholder = { Text("Masukkan Nama Mahasiswa") },
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Person Icon",
                    tint = Color(0xFFFF6F61),
                    modifier = Modifier.size(28.dp)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMhsState.nama != null,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DDBAF),
                unfocusedBorderColor = Color(0xFFB0BEC5),
                errorBorderColor = Color.Red
            )
        )

        if (errorMhsState.nama != null) {
            Text(
                text = errorMhsState.nama,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
        //NIM
        OutlinedTextField(
            value = insertMhsUiEvent.nim,
            onValueChange = {
                onValueChange(insertMhsUiEvent.copy(nim = it))
            },
            label = { Text("NIM") },
            placeholder = { Text("Masukkan NIM") },
            leadingIcon = {
                Icon(
                    Icons.Default.Info,
                    contentDescription = "Ikon NIM",
                    tint = Color(0xFFFF6F61),
                    modifier = Modifier.size(28.dp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = errorMhsState.nim != null,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DDBAF),
                unfocusedBorderColor = Color(0xFFB0BEC5),
                errorBorderColor = Color.Red
            )
        )
        if (errorMhsState.nim != null) {
            Text(
                text = errorMhsState.nim,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        //email
        OutlinedTextField(
            value = insertMhsUiEvent.email,
            onValueChange = {
                onValueChange(insertMhsUiEvent.copy(email = it))
            },
            label = { Text("Email") },
            placeholder = { Text("Masukkan Email") },
            leadingIcon = {
                Icon(
                    Icons.Default.MailOutline,
                    contentDescription = "Email Icon",
                    tint = Color(0xFFFF6F61),
                    modifier = Modifier.size(28.dp)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = errorMhsState.email != null,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DDBAF),
                unfocusedBorderColor = Color(0xFFB0BEC5),
                errorBorderColor = Color.Red
            )
        )

        if (errorMhsState.email != null) {
            Text(
                text = errorMhsState.email,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
        //NIM
        OutlinedTextField(
            value = insertMhsUiEvent.noHp,
            onValueChange = {
                onValueChange(insertMhsUiEvent.copy(noHp = it))
            },
            label = { Text("Nomor HP") },
            placeholder = { Text("Nomor HP") },
            leadingIcon = {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = "Ikon Nomor HP",
                    tint = Color(0xFFFF6F61),
                    modifier = Modifier.size(28.dp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = errorMhsState.noHp != null,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DDBAF),
                unfocusedBorderColor = Color(0xFFB0BEC5),
                errorBorderColor = Color.Red
            )
        )
        if (errorMhsState.noHp != null) {
            Text(
                text = errorMhsState.noHp,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
        DropdownKamar(
            selectedKamar = selectedNoKamar,
            onSelectedKamarChanged = { selectedKamar ->
                selectedNoKamar = selectedKamar
                val selectedIdKamar = kamarOptions.find { it.first == selectedKamar }
                selectedIdKamar?.let { kamar ->
                    onValueChange(insertMhsUiEvent.copy(idKmr = kamar.second.toString()))
                }
            },
            kamarOptions = kamarOptions,
            label = "Pilih Kamar",
            isReadOnly = isReadOnly
        )

        if (errorMhsState.idKmr!= null) {
            Text(
                text = errorMhsState.idKmr,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
    }
}
