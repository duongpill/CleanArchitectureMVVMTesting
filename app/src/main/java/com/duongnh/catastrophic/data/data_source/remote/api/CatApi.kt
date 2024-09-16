package com.duongnh.catastrophic.data.data_source.remote.api

import com.duongnh.catastrophic.data.data_source.remote.dto.CatResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    /**
     * If the mime_types field is empty, the api will return the JPG types automatically
     */
    @GET("search")
    suspend fun getCats(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("mime_types") mime_types: String
    ): Response<List<CatResponse>>

}