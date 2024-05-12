package com.github.mrzahmadi.lightnote.data.api.provider

import android.content.Context
import com.github.mrzahmadi.lightnote.BuildConfig
import com.github.mrzahmadi.lightnote.R
import com.github.mrzahmadi.lightnote.utils.NetworkUtils.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


object OkHttpClientProvider {
    fun createOkHttpClient(context: Context): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            client.addInterceptor(interceptor)
        }
        client.addInterceptor(Interceptor { chain ->
            if (!isInternetAvailable(context)) {
                throw NoConnectivityException(context.getString(R.string.no_internet_connection))
            }
            val request = chain.request()
            chain.proceed(request)
        })
        return client.build()
    }

    class NoConnectivityException(message: String?) : IOException(message)

}