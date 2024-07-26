package com.angelemv.android.pruebaintercamaemv.models.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.angelemv.android.pruebaintercamaemv.models.data.Favorito
import com.angelemv.android.pruebaintercamaemv.models.data.Usuario

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios WHERE username = :username AND password = :password LIMIT 1")
     fun getUsuario(username: String, password: String): Usuario?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(usuario: Usuario)
}


@Dao
interface FavoritoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(favorito: Favorito)

    @Query("SELECT * FROM favoritos")
     fun getFavoritos(): List<Favorito>
}