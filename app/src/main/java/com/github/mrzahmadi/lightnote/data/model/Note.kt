package com.github.mrzahmadi.lightnote.data.model

data class Note(
    val id: Int,
    val title: String,
    val description: String
) {

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
