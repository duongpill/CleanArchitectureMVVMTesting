package com.duongnh.catastrophic.di

import android.content.Context
import com.duongnh.catastrophic.data.data_source.local.dao.CatDao
import com.duongnh.catastrophic.data.data_source.remote.api.CatApi
import com.duongnh.catastrophic.data.repository.CatRepositoryImpl
import com.duongnh.catastrophic.domain.repository.CatRepository
import com.duongnh.catastrophic.domain.use_case.GetCatsUseCase
import com.duongnh.catastrophic.domain.use_case.UpdateCatUseCase
import com.duongnh.catastrophic.utils.Utils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = "https://api.thecatapi.com/v1/images/"
private const val CONNECT_TIMEOUT = 60L
private const val WRITE_TIMEOUT = 60L
private const val READ_TIMEOUT = 60L
private const val API_KEY_NAME = "x-api-key"
// Visit https://api.thecatapi.com to get the token so we can use the load more function
private const val API_KEY_VALUE = ""
private const val CONTENT_TYPE_NAME = "Content-Type"
private const val CONTENT_TYPE_VALUE = "application/json"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(gsonConverterFactory)
            client(okHttpClient)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitHttpClient(headerInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(headerInterceptor)
            addNetworkInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        }.build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header(API_KEY_NAME, API_KEY_VALUE)
                .header(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE)
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideCatApi(retrofit: Retrofit): CatApi {
        return retrofit.create(CatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCatRepository(
        @ApplicationContext context: Context,
        catDao: CatDao,
        catApi: CatApi
    ): CatRepository {
        return CatRepositoryImpl(context, catDao, catApi)
    }

    @Provides
    fun provideGetCatUseCase(catRepository: CatRepository): GetCatsUseCase {
        return GetCatsUseCase(catRepository)
    }

    @Provides
    fun provideUpdateCatUseCase(catRepository: CatRepository): UpdateCatUseCase {
        return UpdateCatUseCase(catRepository)
    }
}