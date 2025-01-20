package com.jodifrkh.asramaapp.ui.view.mahasiswa

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.model.Mahasiswa
import com.jodifrkh.asramaapp.navigation.DestinasiDetailMhs
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.DetailMhsUiState
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.DetailMhsViewModel
import com.jodifrkh.asramaapp.ui.widget.CustomTopAppBar
import com.jodifrkh.asramaapp.ui.widget.OnError
import com.jodifrkh.asramaapp.ui.widget.OnLoading

@Composable
fun DetailMhsScreen(
    onClickBack: () -> Unit,
    viewModel: DetailMhsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getMahasiswaById()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailMhs.titleRes,
                canNavigateBack = true,
                onBackClick = onClickBack,
                refreshImageRes = R.drawable.ic_student,
                onRefresh = { viewModel.getMahasiswaById() }
            )
        }
    ) { innerPadding ->
        DetailMhsStatus(
            modifier = Modifier.padding(innerPadding),
            detailMhsUiState = viewModel.mahasiswaDetailState,
            retryAction = { viewModel.getMahasiswaById() }
        )
    }
}

@Composable
fun DetailMhsStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailMhsUiState: DetailMhsUiState
) {
    when (detailMhsUiState) {
        is DetailMhsUiState.Loading -> OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is DetailMhsUiState.Success -> {
            if (detailMhsUiState.mahasiswa.idMhs.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Data kamar tidak ditemukan",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                AnimatedVisibility(visible = true) {
                    ItemDetailMhs(
                        mahasiswa = detailMhsUiState.mahasiswa,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }

        is DetailMhsUiState.Error -> OnError(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5),
            contentColor = Color(0xFF212121)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Detail Mahasiswa",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1DDBAF),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            ComponentDetailMhs(
                title = "Nama Mahasiswa",
                content = mahasiswa.nama,
                icon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Ikon Nama",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()

            ComponentDetailMhs(
                title = "NIM Mahasiswa",
                content = mahasiswa.nim,
                icon = {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Ikon NIM",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()

            ComponentDetailMhs(
                title = "Nomor Hp Mahasiswa",
                content = mahasiswa.noHp,
                icon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "Ikon Nomor Hp",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()

            ComponentDetailMhs(
                title = "Email Mahasiswa",
                content = mahasiswa.email,
                icon = {
                    Icon(
                        Icons.Default.MailOutline,
                        contentDescription = "Ikon Email",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )
        }
    }
}

@Composable
fun ComponentDetailMhs(
    title: String,
    content: String,
    icon: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.surfaceTint
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
