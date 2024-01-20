package com.github.mrzahmadi.lightnote

import android.content.Context
import com.github.mrzahmadi.lightnote.data.db.AppDatabase
import com.github.mrzahmadi.lightnote.data.db.DatabaseBuilder
import com.github.mrzahmadi.lightnote.data.db.dao.NoteDao
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): AppDatabase {
        return DatabaseBuilder.getInstance(context)
    }

    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }
}