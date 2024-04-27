package com.github.mrzahmadi.lightnote.data.api

import com.github.mrzahmadi.lightnote.data.model.api.Configs
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) : ApiService {
    override suspend fun getConfigs(): Configs {
        return apiService.getConfigs()
    }
}
