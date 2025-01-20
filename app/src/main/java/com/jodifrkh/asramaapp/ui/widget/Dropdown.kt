package com.jodifrkh.asramaapp.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownBangunan(
    selectedName: String,
    onSelectedNameChanged: (String) -> Unit,
    bangunanOptions: List<Pair<String, Int>>,
    label: String,
    modifier: Modifier = Modifier,
    isReadOnly: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded && !isReadOnly,
        onExpandedChange = { if (!isReadOnly) expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedName,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                if (!isReadOnly) {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Bangunan Icon",
                    tint = Color(0xFFFF6F61),
                )
            },
            placeholder = { Text("Pilih Bangunan") },
            readOnly = true,
            enabled = !isReadOnly,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (!isReadOnly) Color(0xFF1DDBAF) else Color.Gray,
                unfocusedBorderColor = Color(0xFFB0BEC5),
                errorBorderColor = Color.Red
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        if (!isReadOnly) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                bangunanOptions.forEach { (namaBgn, idBgn) ->
                    DropdownMenuItem(
                        text = { Text(text = namaBgn) },
                        onClick = {
                            expanded = false
                            onSelectedNameChanged(namaBgn)
                        }
                    )
                }
            }
        }
    }
}