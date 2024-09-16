package com.duongnh.catastrophic.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.duongnh.catastrophic.data.data_source.local.entity.CatEntity

@Dao
interface CatDao {

    @Query("SELECT * FROM Cat")
    fun getAll(): List<CatEntity>

    @Query("SELECT * FROM Cat WHERE id = :id")
    fun getCat(id: String): CatEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Use to replace the old data to avoid the conflict
    suspend fun insertCat(catEntity: CatEntity): Long

    @Delete
    suspend fun deleteCat(catEntity: CatEntity)

    @Query("DELETE FROM Cat")
    suspend fun deleteAll()

    @Update
    suspend fun updateCat(catEntity: CatEntity)

}