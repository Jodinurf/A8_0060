package com.jodifrkh.asramaapp.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.style.TextOverflow
import com.jodifrkh.asramaapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    onBackClick: () -> Unit = { },
    onRefresh: () -> Unit = { },
    onDropdownClick: (String) -> Unit = {},
    refreshImageRes: Int? = null,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var expandedMenu = remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(R.color.primary),
            titleContentColor = Color.White
        ),
        windowInsets = WindowInsets(0.dp),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            if (refreshImageRes != null) {
                Image(
                    painter = painterResource(id = refreshImageRes),
                    contentDescription = "Refresh Image",
                    modifier = Modifier
                        .padding(end = 14.dp)
                        .size(40.dp)
                        .clickable { onRefresh() }
                )
            }

        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Navigate Back",
                        tint = Color.White
                    )
                }
            }
            else {
                IconButton(onClick = { expandedMenu.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
                DropdownMenu(
                    expanded = expandedMenu.value,
                    onDismissRequest = { expandedMenu.value = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            onDropdownClick("Bangunan")
                            expandedMenu.value = false
                        },
                        text = {
                            Text("Bangunan")
                        })
                    DropdownMenuItem(
                        onClick = {
                            onDropdownClick("Kamar")
                            expandedMenu.value = false
                        },
                        text = {
                            Text("Kamar")
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            expandedMenu.value = false
                            onDropdownClick("Mahasiswa")
                        },
                        text = {
                            Text("Mahasiswa")
                        }
                    )
                    DropdownMenuItem(onClick = {
                        expandedMenu.value = false
                        onDropdownClick("Pembayaran Sewa")
                    },
                        text = {
                            Text("Pembayaran Sewa")
                        }
                    )
                }
            }
        }
    )
}
