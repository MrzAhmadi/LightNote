package com.github.mrzahmadi.lightnote.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mrzahmadi.lightnote.data.model.Note.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = TABLE_NAME)
data class Note(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    var title: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("isFavorite")
    val isFavorite: Boolean = false
) : Serializable {

    companion object {
        const val TABLE_NAME = "Note"
    }

}
