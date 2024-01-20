package com.github.mrzahmadi.lightnote.data.repository

import com.github.mrzahmadi.lightnote.data.db.dao.NoteDao
import com.github.mrzahmadi.lightnote.data.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun insert(entity: Note) {
        noteDao.insert(entity)
    }

    suspend fun getAll(): List<Note> {
        return noteDao.getAll()
    }

    suspend fun update(entity: Note) {
        noteDao.update(entity)
    }
}
