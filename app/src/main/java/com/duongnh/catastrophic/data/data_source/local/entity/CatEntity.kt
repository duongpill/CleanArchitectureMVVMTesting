package com.duongnh.catastrophic.data.data_source.local.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cat")
data class CatEntity(
    @PrimaryKey val id: String,
    val url: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val bitmapImg: Bitmap?,
    val width: Int,
    val height: Int
)