package com.duongnh.catastrophic.domain.repository

import com.duongnh.catastrophic.data.data_source.remote.dto.CatRequest
import com.duongnh.catastrophic.domain.MyResult
import com.duongnh.catastrophic.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun getCats(catRequest: CatRequest, isForceGetLocalData: Boolean?): Flow<MyResult<List<Cat>, String>>
}