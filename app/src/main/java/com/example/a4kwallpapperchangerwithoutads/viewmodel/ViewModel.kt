package com.example.a4kwallpapperchangerwithoutads.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.a4kwallpapperchangerwithoutads.network.PicturesAPI
import com.example.a4kwallpapperchangerwithoutads.network.CategoriesData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a4kwallpapperchangerwithoutads.network.PicturesData
import kotlinx.coroutines.launch
import java.lang.Exception


open class ViewModel(app: Application) : ViewModel() {

    val categoryList = MutableLiveData<List<CategoriesData>>()
    val pictureList = MutableLiveData<List<PicturesData>>()
    val currentPicture = MutableLiveData<PicturesData>()
    val hint = MutableLiveData<Boolean>()
    var download = MutableLiveData<Boolean>()
    var setWallpaper = MutableLiveData<Boolean>()
    val goToDetailsLV = MutableLiveData<Boolean>()
    val goToPictureLV = MutableLiveData<Boolean>()
    private val currentData = MutableLiveData<CategoriesData>()
    val status = MutableLiveData<STATUS>()
    private var topic = ""


    fun getCategoryList() {
        viewModelScope.launch {
            status.value = STATUS.LOADING
            try {
                val list = PicturesAPI.service.categoryList()
                categoryList.value = list
                status.value = STATUS.SUCCESS
            } catch (e: Exception) {
                if (categoryList.value.isNullOrEmpty()) {
                    Log.d("ERROR!", e.toString())
                    status.value = STATUS.ERROR
                } else {
                    Log.d("ERROR", categoryList.value.toString())
                    status.value = STATUS.SUCCESS
                }
            }
        }
    }

    fun getPictureList() {
        //Log.d("GD", countryName )
        viewModelScope.launch {
            try {
                val list = PicturesAPI.service.pictureList(topic)
                pictureList.value = list
                Log.d("LIST ", list.toString())
            } catch (e: Exception) {
                pictureList.value = null
                //Log.d("ER ", countryName )
            }
        }
    }


    fun goToDetails(data: CategoriesData) {
        goToDetailsLV.value = true
        currentData.value = data
        topic = currentData.value?.slug.toString()
    }


    fun goToPicture(data: PicturesData) {
        goToPictureLV.value = true
        currentPicture.value = data
    }

    //for fragment changing
    enum class STATUS {
        LOADING,
        ERROR,
        SUCCESS
    }

    fun setBackable() {
        goToDetailsLV.value = false
        goToPictureLV.value = false

    }

    fun wallpaperDownloader(timer: Int, random: Boolean, data: PicturesData?) {
        if (data != null) {
            download.value = true
        }
    }

    fun wallpaperSetter(timer: Int, random: Boolean, data: PicturesData?) {
        if (data != null) {
            setWallpaper.value = true
        }
    }


}
