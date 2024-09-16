package com.duongnh.catastrophic.domain.use_case

import com.duongnh.catastrophic.data.data_source.remote.dto.CatRequest
import com.duongnh.catastrophic.domain.MyResult
import com.duongnh.catastrophic.domain.model.Cat
import com.duongnh.catastrophic.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow

class GetCatsUseCase(private val catRepository: CatRepository) {
    suspend operator fun invoke(
        catRequest: CatRequest,
        isForceGetLocalData: Boolean?
    ): Flow<MyResult<List<Cat>, String>> =
        catRepository.getCats(catRequest, isForceGetLocalData)
}