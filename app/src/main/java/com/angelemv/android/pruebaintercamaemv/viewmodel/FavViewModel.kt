package com.angelemv.android.pruebaintercamaemv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.angelemv.android.pruebaintercamaemv.models.data.Favorito
import com.angelemv.android.pruebaintercamaemv.models.interfaces.FavoritoDao
import com.angelemv.android.pruebaintercamaemv.models.repository.FavRepository
import kotlinx.coroutines.launch

class FavViewModel(private val repository: FavRepository) : ViewModel() {
    private val _favs = MutableLiveData<List<Favorito>>()
    val favs: LiveData<List<Favorito>> get() = _favs

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    init {
        loadFavs()
    }

    private fun loadFavs() {
        viewModelScope.launch {
            try {
                _favs.value = repository.getAllFavs()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}

class FavViewModelFactory(private val repository: FavRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}