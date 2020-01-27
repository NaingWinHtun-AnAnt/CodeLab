package com.ironsight.codelab.activities

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
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

class ImageDownloadActivity : AppCompatActivity() {

    companion object {
        private const val DIR_NAME = "CodeLabImages"
    }

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
        val filename = "filename.jpg"
        val downloadUrlOfImage =
            "https://images.wallpaperscraft.com/image/lens_hand_optics_158012_300x168.jpg"
        val direct = File(
            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + "/" + DIR_NAME + "/"
        )

        if (!direct.exists()) {
            direct.mkdir()
            Log.d("create", "dir created for first time")
        }

        val dm = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(downloadUrlOfImage)
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(filename)
            .setMimeType("image/jpg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                File.separator + DIR_NAME + File.separator + filename
            )

        dm.enqueue(request)
    }
}