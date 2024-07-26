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

    fun searchDrinks(firstLetter: String) {
        _isLoading.value = true // Establecer el estado de carga a true
        viewModelScope.launch {
            try {
                val response = repository.searchDrinks(firstLetter)
                _drinks.value = response.drinks // Aquí extraemos la lista de drinks del response
            } catch (e: Exception) {
                // Manejar error
                Log.e("DrinksViewModel", "Error al buscar bebidas: ${e.message}")
                _errorMessage.value = "Error al cargar los datos. Por favor, verifica tu conexión."
            } finally {
                _isLoading.value = false // Establecer el estado de carga a false
            }
        }
    }

}
