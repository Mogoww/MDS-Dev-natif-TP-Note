package com.example.noteproject.dao

import androidx.room.*
import com.example.noteproject.entities.Note

@Dao
interface NoteDao {

        @Query("SELECT * FROM notes")
        fun getAllNotes(): List<Note>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertNotes(note: Note)

        @Update
        fun updateNote(note: Note)

        @Delete
        fun deleteNote(note: Note)

        @Query("SELECT * FROM notes WHERE id = :id")
        fun getNoteById(id: Int): Note
}