package com.jodifrkh.asramaapp.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jodifrkh.asramaapp.R

@Composable
fun BottomAppBar(
    onClick: (String) -> Unit = {}
) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.12f)
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .shadow(8.dp),
        containerColor = Color.Transparent,
        contentPadding = PaddingValues(0.dp),
        windowInsets = WindowInsets(0),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1DDBAF), Color(0xFF16A085))
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomMenuItem(
                    title = "Bangunan",
                    iconRes = R.drawable.icon_building,
                    onClick = { onClick("Bangunan") }
                )
                BottomMenuItem(
                    title = "Kamar",
                    iconRes = R.drawable.ic_dorm,
                    onClick = { onClick("Kamar") }
                )
                BottomMenuItem(
                    title = "Mahasiswa",
                    iconRes = R.drawable.ic_student,
                    onClick = { onClick("Mahasiswa") }
                )
                BottomMenuItem(
                    title = "Pembayaran",
                    iconRes = R.drawable.icon_payment,
                    onClick = { onClick("Pembayaran") }
                )
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    title: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(
                    if (isClicked) Color.White else Color(0xFF1DDBAF).copy(alpha = 0.1f)
                )
                .clickable {
                    isClicked = true
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                alignment = Alignment.Center
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontSize = 14.sp,
            color = if (isClicked) Color.White else Color.White.copy(alpha = 0.7f),
            fontWeight = if (isClicked) FontWeight.Bold else FontWeight.Medium
        )
    }
}