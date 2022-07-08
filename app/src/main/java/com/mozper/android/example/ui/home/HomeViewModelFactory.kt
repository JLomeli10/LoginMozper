package com.mozper.android.example.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mozper.android.example.data.home.HomeDataSource
import com.mozper.android.example.data.home.HomeRepository

class HomeViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                homeRepository = HomeRepository(
                    HomeDataSource(),
                    application
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}