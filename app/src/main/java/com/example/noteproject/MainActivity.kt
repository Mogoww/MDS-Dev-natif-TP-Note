package com.example.noteproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteproject.Class.Note
import com.example.noteproject.adapter.NoteAdapter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // set "csrjfrjldf" in textView id

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set liste example Notes
        val notes = listOf(
            Note("title1", "description1"),
            Note("title2", "description2"),
            Note("title3", "description3")
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = NoteAdapter(notes)

        // Utilisation de GridLayoutManager pour afficher les notes en 2 colonnes (vous pouvez ajuster le nombre)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter

    }
}

