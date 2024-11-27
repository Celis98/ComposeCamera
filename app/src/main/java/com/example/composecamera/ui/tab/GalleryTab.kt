package com.example.composecamera.ui.tab

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.example.composecamera.logic.ImagesSingleton
import com.example.composecamera.ui.composable.PhotoPreview

@Composable
fun GalleryTab() {
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (ImagesSingleton.images.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(3)
            ) {
                items(
                    count = ImagesSingleton.images.size,
                    key = { it.hashCode() }
                ) { index ->
                    Image(
                        modifier = Modifier.selectable(selected = true, onClick = { selectedImageBitmap = ImagesSingleton.images[index] }),
                        bitmap = ImagesSingleton.images[index].asImageBitmap(),
                        contentDescription = "",
                        contentScale = ContentScale.Fit
                    )
                }
            }
        } else {
            Text(text = "No hay fotos disponibles")
        }
    }
    selectedImageBitmap?.let {
        PhotoPreview(
            bitmap = it
        ) {
            selectedImageBitmap = null
        }
    }
}