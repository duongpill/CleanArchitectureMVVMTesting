package com.duongnh.catastrophic.data.repository

import com.duongnh.catastrophic.data.data_source.remote.dto.CatRequest
import com.duongnh.catastrophic.domain.MyResult
import com.duongnh.catastrophic.domain.model.Cat
import com.duongnh.catastrophic.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCatRepository : CatRepository {

    private val cats = listOf(
        Cat("2lv", "https://cdn2.thecatapi.com/images/2lv.png", 500, 333),
        Cat("3ds", "https://cdn2.thecatapi.com/images/3lv.png", 600, 333),
        Cat("4lv", "https://cdn2.thecatapi.com/images/4lv.png", 500, 333),
        Cat("5lv", "https://cdn2.thecatapi.com/images/5lv.png", 500, 333)
    )

//    private val cats = emptyList<Cat>()

    override suspend fun getCats(
        catRequest: CatRequest,
        isForceGetLocalData: Boolean?
    ): Flow<MyResult<List<Cat>, String>> = flow {
        emit(MyResult.Success(cats))
    }

}