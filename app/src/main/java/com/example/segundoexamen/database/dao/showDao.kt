package com.example.segundoexamen.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.segundoexamen.database.entity.Show
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDao {
    @Query("SELECT * FROM shows ORDER BY name ASC")
    fun getAll(): Flow<List<Show>>

    @Delete
    fun delete(show: Show)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShow(show: Show)

    @Query("SELECT * FROM shows WHERE id = :showId")
    suspend fun getShowById(showId: Int): Show?
}