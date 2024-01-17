package com.github.mrzahmadi.lightnote

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.mrzahmadi.lightnote.data.db.AppDatabase
import com.github.mrzahmadi.lightnote.data.db.dao.NoteDao
import com.github.mrzahmadi.lightnote.data.model.Note
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DataBaseReadWriteTest {
    private lateinit var noteDao: NoteDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context,
            AppDatabase::class.java
        ).build()
        noteDao = db.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeNoteAndReadInList() {
        val note = Note(
            1,
            title = TEST_TITLE,
            description = TEST_DESCRIPTION
        )

        // Test search
        runBlocking {
            noteDao.insert(note)
            val byTitle = noteDao.findByTitle(TEST_TITLE)
            assertThat(byTitle[0], equalTo(note))

            val byDescription = noteDao.findByDescription(TEST_DESCRIPTION)
            assertThat(byDescription[0], equalTo(note))
        }
    }

    companion object {
        private const val TEST_TITLE = "TEST_TITLE"
        private const val TEST_DESCRIPTION = "TEST_DESCRIPTION"
    }
}