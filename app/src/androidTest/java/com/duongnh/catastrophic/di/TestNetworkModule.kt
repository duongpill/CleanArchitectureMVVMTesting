package com.duongnh.catastrophic.di

import android.content.Context
import com.duongnh.catastrophic.data.data_source.local.dao.CatDao
import com.duongnh.catastrophic.data.data_source.remote.api.CatApi
import com.duongnh.catastrophic.data.repository.CatRepositoryImpl
import com.duongnh.catastrophic.domain.repository.CatRepository
import com.duongnh.catastrophic.domain.use_case.GetCatsUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
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

@Module
@InstallIn(SingletonComponent::class)
object TestNetworkModule {

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
    fun provideRetrofitHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(cache) // make your app offline-friendly without a database!
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addNetworkInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 5L * 1024 * 1024
        return Cache(context.cacheDir, cacheSize)
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

}