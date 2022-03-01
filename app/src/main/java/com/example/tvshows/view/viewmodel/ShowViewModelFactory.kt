package com.example.tvshows.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShowViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShowViewModel::class.java)){
            return ShowViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}