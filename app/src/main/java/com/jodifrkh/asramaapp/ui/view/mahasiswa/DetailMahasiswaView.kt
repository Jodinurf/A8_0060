package com.jodifrkh.asramaapp.ui.view.mahasiswa

import CustomTopAppBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jodifrkh.asramaapp.R
import com.jodifrkh.asramaapp.data.ObjectMultipleChoice.kamarChoice
import com.jodifrkh.asramaapp.data.model.Mahasiswa
import com.jodifrkh.asramaapp.navigation.DestinasiDetailMhs
import com.jodifrkh.asramaapp.ui.viewModel.PenyediaViewModel
import com.jodifrkh.asramaapp.ui.viewModel.kamar.HomeKmrViewModel
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.DetailMhsUiState
import com.jodifrkh.asramaapp.ui.viewModel.mahasiswa.DetailMhsViewModel
import com.jodifrkh.asramaapp.ui.widget.OnError
import com.jodifrkh.asramaapp.ui.widget.OnLoading

@Composable
fun DetailMhsScreen(
    onClickBack: () -> Unit,
    onTambahPembayaranClick: (String) -> Unit,
    onLihatRiwayatTransaksiClick: (String) -> Unit,
    onUpdateClick: () -> Unit,
    viewModel: DetailMhsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    kamarViewModel : HomeKmrViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    val kamarList = kamarChoice(kamarViewModel)

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onUpdateClick,
                shape = MaterialTheme.shapes.large,
                containerColor = Color(0xFF1DDBAF)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        DetailMhsStatus(
            modifier = Modifier.padding(innerPadding),
            detailMhsUiState = viewModel.mahasiswaDetailState,
            retryAction = { viewModel.getMahasiswaById() },
            kamarList = kamarList,
            onTambahPembayaranClick = onTambahPembayaranClick,
            onLihatRiwayatTransaksiClick = onLihatRiwayatTransaksiClick
        )
    }
}

@Composable
fun DetailMhsStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailMhsUiState: DetailMhsUiState,
    kamarList: List<Pair<String, Int>>,
    onLihatRiwayatTransaksiClick: (String) -> Unit,
    onTambahPembayaranClick: (String) -> Unit
) {
    when (detailMhsUiState) {
        is DetailMhsUiState.Loading -> OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is DetailMhsUiState.Success -> {
            val mahasiswa = detailMhsUiState.mahasiswa
            if (mahasiswa.idMhs.toString().isEmpty()) {
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
                val idKmrMhs = mahasiswa.idKmr

                val nomorKmr = kamarList.find { it.second == idKmrMhs }?.first
                    ?: "Kamar tidak ditemukan"

                AnimatedVisibility(visible = true) {
                    ItemDetailMhs(
                        mahasiswa = mahasiswa,
                        nomorKmr = nomorKmr,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        onTambahPembayaranClick = { onTambahPembayaranClick(mahasiswa.idMhs.toString()) },
                        onLihatRiwayatTransaksiClick = { onLihatRiwayatTransaksiClick(mahasiswa.idMhs.toString() )}
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
    mahasiswa: Mahasiswa,
    nomorKmr : String,
    onLihatRiwayatTransaksiClick: () -> Unit,
    onTambahPembayaranClick: () -> Unit
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

            Spacer(modifier = Modifier.height(12.dp))
            Divider()

            ComponentDetailMhs(
                title = "Nomor Kamar",
                content = nomorKmr,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_door),
                        contentDescription = "Ikon Email",
                        tint = Color(0xFFFF6F61)
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onLihatRiwayatTransaksiClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF07978F),
                    contentColor = Color.White
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_eye),
                            contentDescription = "Ikon Transaksi",
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                    Text(
                        text = "Lihat Riwayat Transaksi",
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }


            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onTambahPembayaranClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1DDBAF),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Tambah Pembayaran", fontSize = 18.sp)
            }
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
