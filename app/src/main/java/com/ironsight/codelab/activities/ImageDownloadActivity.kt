package com.ironsight.codelab.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ironsight.codelab.DownloadAndSaveImageTask
import com.ironsight.codelab.R
import kotlinx.android.synthetic.main.activity_image_download.*

class ImageDownloadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_download)

        download.setOnClickListener {
            DownloadAndSaveImageTask(this).execute("https://images.wallpaperscraft.com/image/bushes_flowers_yellow_157557_300x168.jpg")
        }
    }
}