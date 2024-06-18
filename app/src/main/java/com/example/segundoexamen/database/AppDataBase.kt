package com.example.segundoexamen.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.segundoexamen.database.dao.ShowDao
import com.example.segundoexamen.database.entity.Show

@Database(entities = [Show::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun showDao(): ShowDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "show_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}