package com.duongnh.catastrophic

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltAndroidApp
class MainApplication: Application(), SingletonImageLoader.Factory {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        SingletonImageLoader.setSafe {
            ImageLoader(context).newBuilder()
                .components { add(OkHttpNetworkFetcherFactory { okHttpClient }) }
                .memoryCachePolicy(CachePolicy.ENABLED)
                .memoryCache {
                    MemoryCache.Builder()
                        .maxSizePercent(context, 0.1)
                        .strongReferencesEnabled(true)
                        .build()
                }
                .diskCachePolicy(CachePolicy.ENABLED)
                .diskCache {
                    DiskCache.Builder()
                        .maxSizePercent(0.03)
                        .directory(cacheDir)
                        .build()
                }
                .logger(DebugLogger())
                .build()
        }
        return SingletonImageLoader.get(applicationContext)
    }
}