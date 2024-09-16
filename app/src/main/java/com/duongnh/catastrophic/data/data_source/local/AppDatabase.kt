package com.duongnh.catastrophic.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.duongnh.catastrophic.data.data_source.local.dao.CatDao
import com.duongnh.catastrophic.data.data_source.local.entity.CatEntity
import com.duongnh.catastrophic.data.utils.BitmapConverters

@Database(entities = [CatEntity::class], version = 1, exportSchema = false)
@TypeConverters(BitmapConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val catDao: CatDao

    companion object {
        const val DB_NAME = "CatGalleryDatabase.db"
    }

}