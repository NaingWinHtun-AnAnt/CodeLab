package com.ironsight.codelab

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference
import java.util.*


class DownloadAndSaveImageTask(context: Context) : AsyncTask<String, Unit, Unit>() {
    private var mContext: WeakReference<Context> = WeakReference(context)

    override fun doInBackground(vararg params: String?) {
        val url = params[0]
        val requestOptions = RequestOptions().override(100)
            .downsample(DownsampleStrategy.DEFAULT)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)

        mContext.get()?.let {
            val bitmap = Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .submit()
                .get()

            //try {
//                var file = it.getDir("Images", Context.MODE_PRIVATE)
//                file = File(file, "iamg.jpg")
//                val out = FileOutputStream(file)

//                var file = File(it.filesDir, "Images")
//                if (!file.exists()) {
//                    file.mkdir()
//                }
//                file = File(file, "img.jpg")
//
//                val stream = FileOutputStream(file)
//
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
//                stream.flush()
//                stream.close()
//                Log.i("Seiggailion", "Image saved : $stream")
//
//            } catch (e: Exception) {
//                Log.i("Seiggailion", "Failed to save image.")
//            }

            val root = getExternalStorageDirectory().toString()
            val myDir = File("$root/saved_images")
            myDir.mkdirs()
            val generator = Random()
            var n = 10000
            n = generator.nextInt(n)
            val fName = "Image-$n.jpg"
            val file = File(myDir, fName)
            if (file.exists()) file.delete()
            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                out.flush()
                out.close()
                Log.i("Seiggailion", "Image saved : $out")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}