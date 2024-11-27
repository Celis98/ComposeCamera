package com.example.composecamera.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.composecamera.ui.composable.CameraTabs
import com.example.composecamera.ui.permission.RequestCameraPermission
import com.example.composecamera.ui.theme.ComposeCameraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCameraTheme {
                var isCameraPermissionGranted by remember { mutableStateOf(false) }
                RequestCameraPermission {
                    isCameraPermissionGranted = it
                }
                if (isCameraPermissionGranted) {
                    CameraTabs()
                }
            }
        }
    }
}