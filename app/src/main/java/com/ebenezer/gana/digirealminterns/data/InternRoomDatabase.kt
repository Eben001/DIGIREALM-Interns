package com.ebenezer.gana.digirealminterns.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ebenezer.gana.digirealminterns.model.Intern

@Database(
    entities = [Intern::class],
    version = 1, exportSchema = false
)
abstract class InternRoomDatabase : RoomDatabase() {

    abstract fun internsDao(): InternsDao

    companion object {

       /* the value of a volatile variable will never be cached, all writes and reads will be
         from the the main memory*/
        @Volatile
        private var INSTANCE: InternRoomDatabase? = null
        fun getDatabase(context: Context): InternRoomDatabase {


           /* wrapping to get the database inside the synchronized block means
            only one thread of execution at a time can enter this block of code*/
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