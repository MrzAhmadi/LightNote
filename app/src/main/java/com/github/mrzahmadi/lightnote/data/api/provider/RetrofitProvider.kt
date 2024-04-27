package com.github.mrzahmadi.lightnote.data.api.provider

import com.github.mrzahmadi.lightnote.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    fun createRetrofit(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {

        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)

        if (BuildConfig.DEBUG)
            builder.client(okHttpClient)

        return builder.build()

    }
}