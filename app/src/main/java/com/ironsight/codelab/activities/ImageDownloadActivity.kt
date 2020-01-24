package com.ironsight.codelab.activities

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ironsight.codelab.R
import kotlinx.android.synthetic.main.activity_image_download.*
import java.io.File
import java.io.FileOutputStream

class ImageDownloadActivity : AppCompatActivity() {

    @SuppressLint("SdCardPath")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_download)

        download.setOnClickListener {
            //DownloadAndSaveImageTask(this).execute("https://images.wallpaperscraft.com/image/man_hood_dark_157650_1280x720.jpg")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        100
                    )
                } else {
                    saveImageToStorage()
                }
            } else {
                saveImageToStorage()
            }
        }

        Glide.with(this)
            .load("/storage/emulated/0/image.jpg")
            .into(imageView)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImageToStorage()
            } else {
                Toast.makeText(
                    this,
                    "Permission is not grant, image can't save to storage.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun saveImageToStorage() {
        val externalStorageState = Environment.getExternalStorageState()
        if (externalStorageState == Environment.MEDIA_MOUNTED) {
            val storageDirectory = Environment.getExternalStorageDirectory().toString()
            val file = File(storageDirectory, "image.jpg")
            try {
                val stream = FileOutputStream(file)
                val drawable = ContextCompat.getDrawable(this, R.drawable.dog_muzzle_shepherd_eyes)
                val bitMap = (drawable as BitmapDrawable).bitmap
                bitMap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
                Toast.makeText(
                    this,
                    "Image save successfully. ${Uri.parse(file.absolutePath)}",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("naingwinhtun", Uri.parse(file.absolutePath).toString())
            } catch (e: Exception) {
                Log.d("naingwinhtun", e.toString())
            }
        }
    }
}