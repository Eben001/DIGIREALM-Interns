package com.ebenezer.gana.digirealminterns

import android.app.Application
import com.ebenezer.gana.digirealminterns.database.InternRoomDatabase

class InternApplication:Application() {

    val database:InternRoomDatabase by lazy{
        InternRoomDatabase.getDatabase(this)
    }

}