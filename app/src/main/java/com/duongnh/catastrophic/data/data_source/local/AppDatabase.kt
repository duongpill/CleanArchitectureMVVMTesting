package com.duongnh.catastrophic.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duongnh.catastrophic.data.data_source.local.dao.CatDao
import com.duongnh.catastrophic.data.data_source.local.entity.CatEntity

@Database(entities = [CatEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract val catDao: CatDao

    companion object {
        const val DB_NAME = "CatGalleryDatabase.db"
    }

}