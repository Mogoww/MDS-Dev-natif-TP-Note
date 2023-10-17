package com.example.noteproject.entities

import androidx.room.*

@Entity(tableName = "notes")
data class Note(val title: String,
                val description: String,
                @PrimaryKey(autoGenerate = false) val id: Int? = null)