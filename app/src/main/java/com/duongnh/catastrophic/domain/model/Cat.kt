package com.duongnh.catastrophic.domain.model

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable

@Immutable
data class Cat(
    val id: String,
    val url: String,
    val bitmapImg: Bitmap?,
    val width: Int,
    val height: Int
) {
    constructor() : this("", "", null, 0, 0)
}