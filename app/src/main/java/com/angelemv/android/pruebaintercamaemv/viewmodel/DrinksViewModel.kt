package com.angelemv.android.pruebaintercamaemv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelemv.android.pruebaintercamaemv.models.data.Drink
import com.angelemv.android.pruebaintercamaemv.models.repository.DrinksRepository
import kotlinx.coroutines.launch

class DrinksViewModel(private val repository: DrinksRepository) : ViewModel() {

    private val _drinks = MutableLiveData<List<Drink>>()
    val drinks: LiveData<List<Drink>> get() = _drinks

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    var isDataLoaded = false

    fun searchDrinksByLetters(startLetter: Char, endLetter: Char) {
        if (isDataLoaded) {
            // Los datos ya están cargados, no es necesario volver a cargar
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val allDrinks = mutableListOf<Drink>()
                for (letter in startLetter..endLetter) {
                    val response = repository.searchDrinks(letter.toString())

                    if (!response.drinks.isNullOrEmpty()) {
                        allDrinks.addAll(response.drinks)
                    }
                }
                _drinks.value = allDrinks
                isDataLoaded = true // Marcar que los datos han sido cargados
            } catch (e: Exception) {
                Log.e("DrinksViewModel", "Error al buscar bebidas: ${e.message}", e)
                _errorMessage.value = "Error al cargar los datos. Por favor, verifica tu conexión."
            } finally {
                _isLoading.value = false
            }
        }
    }


}
