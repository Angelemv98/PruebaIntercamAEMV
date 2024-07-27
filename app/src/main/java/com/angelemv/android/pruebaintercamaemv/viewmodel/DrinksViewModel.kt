package com.angelemv.android.pruebaintercamaemv.viewmodel

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

    private var isDataLoaded = false
    private var currentLetter: Char = 'a'

    fun loadNextDrinks() {
        if (currentLetter > 'z' || isDataLoaded) {
            return
        }
        searchDrinksByLetters(currentLetter, currentLetter)
        currentLetter++
    }

    private fun searchDrinksByLetters(startLetter: Char, endLetter: Char) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.searchDrinks(startLetter.toString())
                if (!response.drinks.isNullOrEmpty()) {
                    val currentDrinks = _drinks.value?.toMutableList() ?: mutableListOf()
                    currentDrinks.addAll(response.drinks)
                    _drinks.value = currentDrinks
                } else {
                    isDataLoaded = true
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar los datos. Por favor, verifica tu conexión."
            } finally {
                _isLoading.value = false
            }
        }
    }
}
