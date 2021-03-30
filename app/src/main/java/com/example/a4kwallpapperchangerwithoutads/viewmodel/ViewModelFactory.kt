package com.example.a4kwallpapperchangerwithoutads.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Factory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.a4kwallpapperchangerwithoutads.viewmodel.ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}