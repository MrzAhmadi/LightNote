package com.github.mrzahmadi.lightnote.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Note(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String
) : Serializable {

    companion object {
        fun getTestingList(): ArrayList<Note> {
            val noteList = arrayListOf<Note>()
            for (i in 1..10) {
                noteList.add(
                    Note(
                        i,
                        "Title $i",
                        "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an..."
                    )
                )
            }
            return noteList
        }
    }

}
