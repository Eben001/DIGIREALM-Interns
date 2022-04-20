package com.ebenezer.gana.digirealminterns

import android.app.Application
import com.ebenezer.gana.digirealminterns.data.InternRoomDatabase

class InternApplication:Application() {


    val database:InternRoomDatabase by lazy{
        InternRoomDatabase.getDatabase(this)
    }

}