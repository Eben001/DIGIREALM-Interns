package com.ebenezer.gana.digirealminterns.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Intern::class],
    version = 1, exportSchema = false
)
abstract class InternRoomDatabase : RoomDatabase() {

    abstract fun internsDao(): InternsDao

    companion object {

        @Volatile
        private var INSTANCE: InternRoomDatabase? = null
        fun getDatabase(context: Context): InternRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InternRoomDatabase::class.java,
                    "intern_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                return instance
            }
        }

    }

}