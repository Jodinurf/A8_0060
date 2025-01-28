package com.jodifrkh.asramaapp.ui.view.bangunan

import CustomTopAppBar
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
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
import com.jodifrkh.asramaapp.data.model.Bangunan
import com.jodifrkh.asramaapp.navigation.DestinasiDetailBgn
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.DetailBgnUiState
import com.jodifrkh.asramaapp.ui.viewModel.bangunan.DetailBgnViewModel
import com.jodifrkh.asramaapp.ui.widget.OnError
import com.jodifrkh.asramaapp.ui.widget.OnLoading

@Composable
fun DetailBgnScreen(
    onClickBack: () -> Unit,
    onUpdateClick: () -> Unit,
    viewModel: DetailBgnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getBangunanById()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailBgn.titleRes,
                canNavigateBack = true,
                onBackClick = onClickBack,
                refreshImageRes = R.drawable.icon_building,
                onRefresh = { viewModel.getBangunanById() }
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
                    contentDescription = "Edit Bangunan",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        DetailBgnStatus(
            modifier = Modifier.padding(innerPadding),
            detailBgnUiState = viewModel.bangunanDetailState,
            retryAction = { viewModel.getBangunanById() }
        )
    }
}

@Composable
fun DetailBgnStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailBgnUiState: DetailBgnUiState
) {
    when (detailBgnUiState) {
        is DetailBgnUiState.Loading -> OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is DetailBgnUiState.Success -> {
            if (detailBgnUiState.bangunan.namaBgn.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Data Bangunan tidak ditemukan",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                AnimatedVisibility(visible = true) {
                    ItemDetailBgn(
                        bangunan = detailBgnUiState.bangunan,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }

        is DetailBgnUiState.Error -> OnError(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailBgn(
    modifier: Modifier = Modifier,
    bangunan: Bangunan
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
                text = "Detail Bangunan",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1DDBAF),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            ComponentDetailBgn(
                title = "Nama Bangunan",
                content = bangunan.namaBgn,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Ikon Nama Bangunan",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()

            ComponentDetailBgn(
                title = "Alamat",
                content = bangunan.alamat,
                icon = {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Ikon Alamat",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider()

            ComponentDetailBgn(
                title = "Jumlah Lantai",
                content = "${bangunan.jmlhLantai} lantai",
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.stairs_icon),
                        contentDescription = "Ikon Jumlah Lantai",
                        tint = Color(0xFFFFC107)
                    )
                }
            )
        }
    }
}

@Composable
fun ComponentDetailBgn(
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
