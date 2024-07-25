package com.angelemv.android.pruebaintercamaemv.models.interfaces

import com.angelemv.android.pruebaintercamaemv.models.data.DrinkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("search.php")
    suspend fun searchDrinks(@Query("f") firstLetter: String): DrinkResponse
}