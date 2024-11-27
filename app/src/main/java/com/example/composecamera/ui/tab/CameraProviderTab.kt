package com.example.composecamera.ui.tab

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.composecamera.logic.ImagesSingleton
import com.example.composecamera.logic.ZoomLevel
import com.example.composecamera.logic.getCameraProvider
import com.example.composecamera.logic.takePicture
import com.example.composecamera.ui.composable.CameraUIControls

@Composable
fun CameraProviderTab() {

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }
    var isFlashEnabled by remember { mutableStateOf(false) }
    var isUsingFrontCamera by remember { mutableStateOf(false) }
    var zoom by remember { mutableStateOf(ZoomLevel.X1) }

    val imageCapture = remember {
        ImageCapture.Builder()
            .build()
    }

    LaunchedEffect(isUsingFrontCamera, zoom) {
        val cameraProvider = context.getCameraProvider()
        val lensFacing =
            if (isUsingFrontCamera) CameraSelector.LENS_FACING_FRONT
            else CameraSelector.LENS_FACING_BACK
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        val preview = Preview.Builder().build().also {
            it.surfaceProvider = previewView.surfaceProvider
        }

        cameraProvider.let {
            it.unbindAll()
            val camera = it.bindToLifecycle(
                lifecycleOwner = lifecycleOwner,
                cameraSelector = cameraSelector,
                imageCapture,
                preview
            )
            camera.cameraControl.setLinearZoom(zoom.factor)
        }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        takePicture(
                            context = context,
                            imageCapture = imageCapture,
                            flashMode = if (isFlashEnabled) FLASH_MODE_ON else FLASH_MODE_OFF,
                            onPhotoTaken = { image ->
                                ImagesSingleton.images.add(image)
                            }
                        )
                    }
                ) {
                    Text(text = "Take picture")
                }
            }
        }
    ) {
        Column {
            Row(modifier = Modifier.padding(8.dp)) {
                CameraUIControls(
                    isFlashEnabled = isFlashEnabled,
                    isUsingFrontCamera = isUsingFrontCamera,
                    zoom = zoom
                ) { newFlashEnabled, newFrontCamera, newZoom ->
                    isFlashEnabled = newFlashEnabled
                    isUsingFrontCamera = newFrontCamera
                    zoom = newZoom
                }
            }
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                factory = { previewView }
            )
        }
    }
}