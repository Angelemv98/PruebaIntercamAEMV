package com.angelemv.android.pruebaintercamaemv.models.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuario")
    fun getAll():List<UsuarioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: UsuarioEntity)




}