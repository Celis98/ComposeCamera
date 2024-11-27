package com.example.composecamera.logic

import android.content.ContentValues
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.composecamera.R

@Composable
fun SaveBitmap(
    bitmap: Bitmap,
    onBitmapSaved:() -> Unit
) {
    val context = LocalContext.current
    val timestamp = System.currentTimeMillis()

    val values = ContentValues()
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
    values.put(MediaStore.Images.Media.DATE_ADDED, timestamp)
    values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp)
    values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + context.getString(R.string.app_name))
    values.put(MediaStore.Images.Media.IS_PENDING, true)
    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    if (uri != null) {
        try {
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.close()
                } catch (e: Exception) {
                    Log.d("TEST--", "save bitmap $e")
                }
            }
            values.put(MediaStore.Images.Media.IS_PENDING, false)
            context.contentResolver.update(uri, values, null, null)

            Toast.makeText(context, "Bitmap saved!", Toast.LENGTH_SHORT).show()
            onBitmapSaved()
        } catch (e: Exception) {
            Log.d("TEST--", "save bitmap $e")
        }
    }
}