package com.angelemv.android.pruebaintercamaemv.models.repository

import com.angelemv.android.pruebaintercamaemv.models.data.DrinkResponse
import com.angelemv.android.pruebaintercamaemv.models.interfaces.CocktailApi

class DrinksRepository(private val api: CocktailApi) {

    suspend fun searchDrinks(firstLetter: String): DrinkResponse {
        return api.searchDrinks(firstLetter)
    }
}