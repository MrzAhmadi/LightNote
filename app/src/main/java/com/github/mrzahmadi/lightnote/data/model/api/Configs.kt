package com.github.mrzahmadi.lightnote.data.model.api

import androidx.annotation.Keep
import com.github.mrzahmadi.lightnote.BuildConfig
import com.google.gson.annotations.SerializedName

@Keep
data class Configs(
    @SerializedName("version_code") val versionCode: Int,
    @SerializedName("version_name") val versionName: String
) {

    fun isThereNewVersion() : Boolean {
       return versionCode > BuildConfig.VERSION_CODE
    }

}