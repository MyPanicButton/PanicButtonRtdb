package com.example.panicbuttonrtdb.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        // Periksa apakah modelClass adalah MyViewModel
        if (modelClass.isAssignableFrom(ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

