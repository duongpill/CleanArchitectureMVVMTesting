package com.duongnh.catastrophic.data.mapper

import com.duongnh.catastrophic.data.data_source.local.entity.CatEntity
import com.duongnh.catastrophic.data.data_source.remote.dto.CatResponse
import com.duongnh.catastrophic.domain.model.Cat

fun CatResponse.toEntity() = CatEntity(id, url, null, width, height)

fun CatEntity.toModel() = Cat(id, url, bitmapImg, width, height)

fun CatResponse.toModel() = Cat(id, url, null, width, height)