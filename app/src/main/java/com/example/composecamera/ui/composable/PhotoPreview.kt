package com.example.composecamera.ui.composable

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composecamera.logic.SaveBitmap

@Composable
fun PhotoPreview(
    bitmap: Bitmap,
    onDismissRequest:() -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {
            ConstraintLayout {
                val (photo, save) = createRefs()
                Image(
                    modifier = Modifier.constrainAs(photo) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                    bitmap = bitmap.asImageBitmap(),
                    contentScale = ContentScale.Fit,
                    contentDescription = ""
                )
                var shouldSaveImage by remember { mutableStateOf(false) }
                Button(
                    modifier = Modifier.constrainAs(save) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, 16.dp)
                    },
                    onClick = {
                        shouldSaveImage = true
                    }
                ) {
                    Text(text = "Guardar")
                }
                if (shouldSaveImage) {
                    SaveBitmap(bitmap) {
                        shouldSaveImage = false
                    }
                }
            }
        }
    }
}