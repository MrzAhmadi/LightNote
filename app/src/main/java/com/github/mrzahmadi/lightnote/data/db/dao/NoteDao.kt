package com.github.mrzahmadi.lightnote.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.mrzahmadi.lightnote.data.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM ${Note.TABLE_NAME}")
    suspend fun getAll(): List<Note>

    @Update
    suspend fun update(note: Note)
}