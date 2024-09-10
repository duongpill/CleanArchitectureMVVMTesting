package com.duongnh.catastrophic.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cat")
data class CatEntity(
    @PrimaryKey val id: String,
    val url: String,
    val width: Int,
    val height: Int
)