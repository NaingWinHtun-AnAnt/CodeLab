package com.ironsight.codelab.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ironsight.codelab.DownloadAndSaveImageTask
import kotlinx.android.synthetic.main.activity_image_download.*


class ImageDownloadActivity : AppCompatActivity() {

    @SuppressLint("SdCardPath")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ironsight.codelab.R.layout.activity_image_download)

        download.setOnClickListener {
            DownloadAndSaveImageTask(this).execute("https://images.wallpaperscraft.com/image/bridge_city_night_157572_300x168.jpg")
        }

        Glide.with(this)
            .load("/data/user/0/com.ironsight.codelab/files/Images/img.jpg")
            .into(imageView)
    }
}