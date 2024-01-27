package com.github.mrzahmadi.lightnote.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("SELECT * FROM ${Note.TABLE_NAME} where title=:title")
    suspend fun findByTitle(title: String): List<Note>

    @Query("SELECT * FROM ${Note.TABLE_NAME} where description=:description")
    suspend fun findByDescription(description: String): List<Note>

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM ${Note.TABLE_NAME} where isFavorite=true")
    suspend fun getFavoriteList(): List<Note>

}