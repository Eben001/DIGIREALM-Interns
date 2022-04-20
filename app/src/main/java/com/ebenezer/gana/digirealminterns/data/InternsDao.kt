package com.ebenezer.gana.digirealminterns.data

import androidx.room.*
import com.ebenezer.gana.digirealminterns.model.Intern
import kotlinx.coroutines.flow.Flow

@Dao
interface InternsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(intern: Intern)

    @Update
    suspend fun update(intern: Intern)

    @Delete
    suspend fun delete(intern: Intern)

    @Query("SELECT * FROM intern WHERE id = :id")
    fun getIntern(id:Int): Flow<Intern>

    @Query("SELECT * FROM intern ORDER BY id ASC")
    fun getInterns():Flow<List<Intern>>

}