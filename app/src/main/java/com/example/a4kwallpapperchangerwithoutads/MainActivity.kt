package com.example.a4kwallpapperchangerwithoutads

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import com.example.a4kwallpapperchangerwithoutads.viewmodel.Factory
import com.example.a4kwallpapperchangerwithoutads.viewmodel.ViewModel
import java.io.File


lateinit var viewModel: ViewModel

class MainActivity : AppCompatActivity() {
    var myDownloadId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //lock portrait orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val factory = Factory(application)
        viewModel = ViewModelProvider(this, factory).get(ViewModel::class.java)
        viewModel.hint.value = true
        viewModel.download.value = false

        viewModel.download.observe(this, Observer {
            if (viewModel.download.value!!) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    downloadWallpaper()
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

    private fun downloadWallpaper() {
        val request: DownloadManager.Request = DownloadManager.Request(
                Uri.parse(viewModel.currentPicture.value?.links?.download.toString() + "?force=true"))
                .setTitle("${viewModel.currentPicture.value?.id}")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverMetered(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${viewModel.currentPicture.value?.id}.jpg")

        val dm: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        myDownloadId = dm.enqueue(request)

        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id: Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == myDownloadId) {
//                    Toast.makeText(applicationContext, "Wallpaper successfully downloaded", Toast.LENGTH_SHORT).show()
                    wallpaperSet()
                }
            }
        }
        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }



    private fun wallpaperSet() {
        Toast.makeText(this, "Wallpaper set!", Toast.LENGTH_SHORT).show()
        val uri: Uri = Uri.parse("${Environment.DIRECTORY_DOWNLOADS}/${viewModel.currentPicture.value?.id}.jpg")
        Log.d("ERR ", uri.normalizeScheme().toString() )
//        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(this)
//        wallpaperManager.getCropAndSetWallpaperIntent(uri.normalizeScheme())

    }


}














