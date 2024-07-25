package com.angelemv.android.pruebaintercamaemv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angelemv.android.pruebaintercamaemv.models.repository.DrinksRepository

class DrinksViewModelFactory(private val repository: DrinksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}