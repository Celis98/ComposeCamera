package com.example.composecamera.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composecamera.logic.ZoomLevel

@Composable
fun CameraUIControls(
    isFlashEnabled: Boolean,
    isUsingFrontCamera: Boolean,
    zoom: ZoomLevel,
    updateStates: (Boolean, Boolean, ZoomLevel) -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        IconButton(onClick = { updateStates(!isFlashEnabled, isUsingFrontCamera, zoom) }) {
            Icon(
                imageVector = if (isFlashEnabled) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Toggle Flash"
            )
        }
        IconButton(onClick = { updateStates(isFlashEnabled, !isUsingFrontCamera, zoom) }) {
            Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Switch Camera")
        }
        Button(onClick = {
            val newZoom = when (zoom) {
                ZoomLevel.X1 -> ZoomLevel.X2
                ZoomLevel.X2 -> ZoomLevel.X1
            }
            updateStates(isFlashEnabled, isUsingFrontCamera, newZoom)
        }) {
            Text(text = zoom.name)
        }
    }
}