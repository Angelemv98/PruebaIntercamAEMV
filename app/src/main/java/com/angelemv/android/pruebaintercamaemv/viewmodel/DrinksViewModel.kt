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

     fun searchDrinks(firstLetter: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchDrinks(firstLetter)
                _drinks.value = response.drinks // Aquí extraemos la lista de drinks del response
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }


}