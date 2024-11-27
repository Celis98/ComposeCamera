package com.example.composecamera.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composecamera.ui.tab.CameraProviderTab
import com.example.composecamera.ui.tab.GalleryTab

@Composable
fun CameraTabs() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf(
        Pair("Camara", Icons.Default.Build),
        Pair("Galeria", Icons.Default.PlayArrow)
    )

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, item ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    icon = { Icon(item.second, "") },
                    text = { Text(text = item.first, modifier = Modifier.padding(16.dp)) }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> CameraProviderTab()
            1 -> GalleryTab()
        }
    }
}