package com.angelemv.android.pruebaintercamaemv.models.repository

import com.angelemv.android.pruebaintercamaemv.models.data.Favorito
import com.angelemv.android.pruebaintercamaemv.models.interfaces.FavoritoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavRepository(private val favoritoDao: FavoritoDao) {
    suspend fun getAllFavs(): List<Favorito> {
        return withContext(Dispatchers.IO) {
            favoritoDao.getFavoritos()
        }
    }
    suspend fun getbyIdDrink(cocktailId: String): Favorito? {
        return withContext(Dispatchers.IO) {
            favoritoDao.getFavoritoByCocktailId(cocktailId)
        }
    }
}