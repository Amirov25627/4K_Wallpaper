package com.example.a4kwallpapperchangerwithoutads

import android.Manifest
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.*
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.a4kwallpapperchangerwithoutads.viewmodel.Factory
import com.example.a4kwallpapperchangerwithoutads.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL


lateinit var viewModel: ViewModel

class MainActivity : AppCompatActivity() {
    var myDownloadId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //lock portrait orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //lock Night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        setContentView(R.layout.activity_main)

        val factory = Factory(application)
        viewModel = ViewModelProvider(this, factory).get(ViewModel::class.java)
        viewModel.hint.value = true
        viewModel.download.value = false
        viewModel.setWallpaper.value = false

        viewModel.download.observe(this, Observer {
            if (viewModel.download.value!!) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    downloadWallpaper()
                } else {
                    askPermission()
                }
            }
        })

        viewModel.setWallpaper.observe(this, Observer {
            if (viewModel.setWallpaper.value!!) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    wallpaperSetter()
                } else {
                    askPermission()
                }
            }
        })
    }


    private fun askPermission() {
        Toast.makeText(this, "Need permission", Toast.LENGTH_SHORT).show()
        if (ContextCompat.checkSelfPermission(this@MainActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !==
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) ===
                                    PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                        downloadWallpaper()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

     private fun wallpaperSetter(){
        lifecycleScope.launch(Dispatchers.IO) {
            try {
             setWallpaper()
            } catch (e: Exception) {
                Log.d("ERRR ", e.toString())
            }
        }
         Toast.makeText(this, "Wallpaper setting done!", Toast.LENGTH_SHORT).show()
         viewModel.download.value = false
         viewModel.setWallpaper.value = false
    }

    private fun downloadWallpaper() {
        val uriString = viewModel.currentPicture.value?.links?.download.toString() + "?force=true"
        val title = viewModel.currentPicture.value?.id
        val request: DownloadManager.Request = DownloadManager.Request(
                Uri.parse(uriString))
                .setTitle("$title")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverMetered(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${viewModel.currentPicture.value?.id}.jpg")

        Log.d("uriString: ", uriString)
        Log.d("title: ", title.toString())
        val dm: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        myDownloadId = dm.enqueue(request)

//        val br = object : BroadcastReceiver() {
////            @RequiresApi(Build.VERSION_CODES.Q)
////            override fun onReceive(context: Context?, intent: Intent?) {
////                val id: Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
////                if (id == myDownloadId) {
//////                    Toast.makeText(applicationContext, "Wallpaper successfully downloaded", Toast.LENGTH_SHORT).show()
////                   lifecycleScope.launch(Dispatchers.IO) {
////                       try {
////                            downloadWallpaper()
//////                            setWallpaper()
////                       } catch (e: Exception) {
////                            Log.d("ERRR ", e.toString())
////                        }
////                    }
////                }
////            }
//        }
        Toast.makeText(this, "Downloading done!", Toast.LENGTH_SHORT).show()
//        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        viewModel.download.value = false
        viewModel.setWallpaper.value = false
    }

//fun setWallpaper(){
//    val file = File(Environment.DIRECTORY_DOWNLOADS, "${viewModel.currentPicture.value?.id}.jpg")
//    val path = file.absolutePath
//    val imageFile = File(path)
//
//    if (imageFile.exists()) {
//        val bitmap: Bitmap = BitmapFactory.decodeFile(path)
//        val mWallpaperManager = WallpaperManager.getInstance(this)
//         mWallpaperManager.setBitmap(bitmap)
//
//
//    }




    private fun setWallpaper() {
        val wpm = WallpaperManager.getInstance(this)
        val ins: InputStream = URL(viewModel.currentPicture.value?.urls?.full.toString()).openStream()
        wpm.setStream(ins)
        }




//    @RequiresApi(Build.VERSION_CODES.Q)
//    private fun setWallpaper() {
////        val uri: Uri = Uri.fromFile(File("file:///${Environment.DIRECTORY_DOWNLOADS}/${viewModel.currentPicture.value?.id}.jpg"))
//        val uri: Uri = FileProvider.getUriForFile(this, this.applicationContext.packageName.toString() + ".provider", File("${Environment.DIRECTORY_DOWNLOADS}/${viewModel.currentPicture.value?.id}.jpg") )
////        val uri: Uri = Uri.parse(viewModel.currentPicture.value?.urls?.full.toString())
////        val wallpaper = getImageContentUri(this, uri.toString())
//        Log.d("WLP", uri.toString())
//        val intent = Intent(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER)
//        val mime = "image/*"
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        intent.setDataAndType(uri, mime)
////        try {
//            startActivity(intent)
//            Log.d("STRT", intent.toString())
////        } catch (e: ActivityNotFoundException) {
////            Log.d("EEE", e.toString())
////        }
//    }


    //convert to Image URI
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getImageContentUri(context: Context, absPath: String): Uri? {
        val cursor = context.contentResolver.query(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI
                , arrayOf(MediaStore.Downloads._ID), MediaStore.Downloads.DATA.toString() + "=? ", arrayOf(absPath), null
        )
        return if (cursor != null && cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.DownloadColumns._ID))
            Uri.withAppendedPath(MediaStore.Downloads.EXTERNAL_CONTENT_URI, id.toString())
            } else if(!absPath.isNullOrEmpty()){
                val values = ContentValues()
                values.put(MediaStore.Downloads.DATA, absPath)
                context.contentResolver.insert(
                        MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            } else {null}
    }
}














