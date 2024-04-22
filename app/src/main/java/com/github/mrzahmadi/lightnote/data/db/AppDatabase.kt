package com.github.mrzahmadi.lightnote.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.mrzahmadi.lightnote.data.db.dao.NoteDao
import com.github.mrzahmadi.lightnote.data.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}