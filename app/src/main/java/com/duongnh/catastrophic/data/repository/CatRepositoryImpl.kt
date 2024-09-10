package com.duongnh.catastrophic.data.repository

import android.content.Context
import com.duongnh.catastrophic.data.data_source.local.dao.CatDao
import com.duongnh.catastrophic.data.data_source.remote.api.CatApi
import com.duongnh.catastrophic.data.data_source.remote.dto.CatRequest
import com.duongnh.catastrophic.data.mapper.toEntity
import com.duongnh.catastrophic.data.mapper.toModel
import com.duongnh.catastrophic.domain.MyResult
import com.duongnh.catastrophic.domain.model.Cat
import com.duongnh.catastrophic.domain.repository.CatRepository
import com.duongnh.catastrophic.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatRepositoryImpl(
    private val context: Context,
    private val catDao: CatDao,
    private val catApi: CatApi
) : CatRepository {

    /**
     * We will check the network to get cats, if the network is available, we will get from the API, otherwise we will get from the DB
     */
    override suspend fun getCats(
        catRequest: CatRequest,
        isForceGetLocalData: Boolean?
    ): Flow<MyResult<List<Cat>, String>> = flow {
        val isNetworkAvailable = Utils.isNetworkAvailable(context)

        val catsFromDB = catDao.getAll().let {
            it.map { catEntity ->
                catEntity.toModel()
            }
        }

        if (!isNetworkAvailable || (isForceGetLocalData == true && catsFromDB.isNotEmpty())) {
            emit(MyResult.Success(catsFromDB))
            return@flow
        }

        val response = catApi.getCats(catRequest.limit, catRequest.page, catRequest.mime_types)
        if (response.isSuccessful) {
            catDao.deleteAll()

            val catsFromApi = response.body()?.let { list ->
                list.map { catResponse ->
                    // Insert Cat to DB
                    catDao.insertCat(catResponse.toEntity())
                    catResponse.toModel()
                }
            } ?: emptyList()

            emit(MyResult.Success(catsFromApi))
        } else {
            emit(MyResult.Error(response.errorBody()?.string() ?: "Error loading cats"))
        }
    }
}