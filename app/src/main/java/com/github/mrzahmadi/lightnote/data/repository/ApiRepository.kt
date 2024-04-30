package com.github.mrzahmadi.lightnote.data.repository

import com.github.mrzahmadi.lightnote.data.api.ApiService
import com.github.mrzahmadi.lightnote.data.model.api.Configs
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getConfigs(): Configs {
        return apiService.getConfigs()
    }

}