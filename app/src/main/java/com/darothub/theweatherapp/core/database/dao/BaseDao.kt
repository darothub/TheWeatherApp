package com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: T)
}