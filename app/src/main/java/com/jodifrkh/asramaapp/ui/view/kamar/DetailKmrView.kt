package com.jodifrkh.asramaapp.ui.view.kamar

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.model.Kamar
import com.jodifrkh.asramaapp.navigation.DestinasiDetailKmr
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.DetailKmrUiState
import com.jodifrkh.asramaapp.ui.viewModel.kamar.DetailKmrViewModel
import com.jodifrkh.asramaapp.ui.widget.CustomTopAppBar

@Composable
fun DetailKmrScreen(
    onClickBack: () -> Unit,
    onUpdateClick: () -> Unit,
    viewModel: DetailKmrViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getKamarById()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailKmr.titleRes,
                canNavigateBack = true,
                onBackClick = onClickBack,
                refreshImageRes = R.drawable.ic_bedroom,
                onRefresh = { viewModel.getKamarById() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onUpdateClick,
                shape = MaterialTheme.shapes.large,
                containerColor = Color(0xFF1DDBAF)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit kamar",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        DetailKmrStatus(
            modifier = Modifier.padding(innerPadding),
            detailKmrUiState = viewModel.kamarDetailState,
            retryAction = { viewModel.getKamarById() }
        )
    }
}

@Composable
fun DetailKmrStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailKmrUiState: DetailKmrUiState
) {
    when (detailKmrUiState) {
        is DetailKmrUiState.Loading -> OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is DetailKmrUiState.Success -> {
            if (detailKmrUiState.kamar.idKmr.toString().isEmpty()) {
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
                    ItemDetailKmr(
                        kamar = detailKmrUiState.kamar,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }

        is DetailKmrUiState.Error -> OnError(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailKmr(
    modifier: Modifier = Modifier,
    kamar: Kamar
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
                text = "Detail Kamar",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1DDBAF),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            ComponentDetailKmr(
                title = "Nomor kamar",
                content = kamar.nomorKmr,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_door),
                        contentDescription = "Ikon Nomor kamar",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()

            ComponentDetailKmr(
                title = "Kapasitas",
                content = kamar.kapasitas.toString(),
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_capacity),
                        contentDescription = "Ikon Kapasitas",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()

            ComponentDetailKmr(
                title = "Status Kamar",
                content = kamar.statusKmr,
                icon = {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Ikon Status",
                        tint = Color(0xFFFFC107)
                    )
                }
            )
        }
    }
}

@Composable
fun ComponentDetailKmr(
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
