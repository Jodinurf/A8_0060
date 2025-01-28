package com.jodifrkh.asramaapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jodifrkh.asramaapp.navigation.MainControllerPage
@Composable
fun AsramaApp() {
    Scaffold {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) { MainControllerPage() }
    }
}