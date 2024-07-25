package com.angelemv.android.pruebaintercamaemv.models.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [UsuarioEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(ctx: Context, scope: CoroutineScope): AppDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    AppDatabase::class.java,
                    "usuario"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                this.instance = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                instance?.let { database ->
                    scope.launch {
                        populateDatabase(database.usuarioDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(usuarioDao: UsuarioDao) {

            // Inserta el usuario inicial
            val initialUser = UsuarioEntity(user = "Admin", password = "prueba123")
            usuarioDao.insert(initialUser)
        }
    }
}
