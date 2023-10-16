package com.example.noteproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.noteproject.Class.Note

// set liste example Notes
val notes = listOf(
    Note("title1", "description1"),
    Note("title2", "description2"),
    Note("title3", "description3")
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // set "csrjfrjldf" in textView id

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // display list of notes with note_item layout in recyclerView id
        val recyclerView = findViewById<TextView>(R.id.recyclerView)
        // recyclerView.text = notes.toString()


    }
}

