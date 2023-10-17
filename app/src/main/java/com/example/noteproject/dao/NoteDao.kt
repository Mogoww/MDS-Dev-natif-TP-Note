package com.example.noteproject.dao

import androidx.room.*
import com.example.noteproject.entities.Note

@Dao
interface NoteDao {

        @Query("SELECT * FROM notes")
        fun getAllNotes(): List<Note>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertNotes(note: Note)
}