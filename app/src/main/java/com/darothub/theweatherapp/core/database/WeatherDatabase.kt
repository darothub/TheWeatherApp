package com.darothub.theweatherapp.com.darothub.theweatherapp.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.ForecastDayDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.HourDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.database.dao.WeatherDao
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.ForecastDayAndHourCrossRef
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.ForecastDayEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.HourEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.WeatherEntity
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.Converters

@Database(
    entities = [ WeatherEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            WeatherDatabase::class.java, "weatherdb"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}