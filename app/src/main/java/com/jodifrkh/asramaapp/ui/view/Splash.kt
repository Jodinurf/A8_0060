package com.jodifrkh.asramaapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jodifrkh.asramaapp.R

@Preview(showBackground = true)
@Composable
fun SplashView(
    onMulaiClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.primary)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(R.drawable.ic_papapas),
            contentDescription = "Logo",
            modifier = Modifier.size(280.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Text Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Selamat Datang di",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 22.sp
            )
            Text(
                text = "PapaPas Mobile App",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.width(150.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Papa Pengelola Asrama",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Button
        Button(
            onClick = {
                onMulaiClick()
            },
            modifier = Modifier
                .width(250.dp)
                .height(50.dp)
                .shadow(8.dp, RoundedCornerShape(25.dp)),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = colorResource(R.color.primary)
            )
        ) {
            Text(text = "Mulai", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Footer text
        Text(
            text = "Powered by Jodi Nur Farkhani",
            color = Color.White.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodySmall,
            fontSize = 14.sp
        )
    }
}