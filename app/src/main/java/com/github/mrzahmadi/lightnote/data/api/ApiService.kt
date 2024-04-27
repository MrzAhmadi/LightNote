package com.github.mrzahmadi.lightnote.data.api

import com.github.mrzahmadi.lightnote.data.model.api.Configs
import retrofit2.http.GET

interface ApiService {
    @GET("configs.json")
    suspend fun getConfigs(): Configs
}