package com.example.noteproject.database

import android.content.Context
import com.example.noteproject.entities.Note
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.noteproject.dao.NoteDao

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NoteDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "notes_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}