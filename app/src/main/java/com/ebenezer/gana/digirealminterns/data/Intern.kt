package com.ebenezer.gana.digirealminterns.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "intern")
data class Intern(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val internName:String,
    @ColumnInfo(name = "skills")
    val internAcquiredSkills:String
)