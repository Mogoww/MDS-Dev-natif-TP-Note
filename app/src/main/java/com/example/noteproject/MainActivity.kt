package com.example.noteproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteproject.Class.Note
import com.example.noteproject.adapter.NoteAdapter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var notes = listOf(
            Note("title1", "description1"),
            Note("title2", "description2"),
            Note("title3", "description3")
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = NoteAdapter(notes)

        //val layoutManager = GridLayoutManager(this, 2)
        //recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val searchView: SearchView = findViewById(R.id.search_view)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText)
                return true
            }
        })


        val addNoteButton: Button = findViewById(R.id.add_note_button)
        addNoteButton.setOnClickListener {
           notes = notes + Note(
               "title ${notes.size + 1}",
               "description ${notes.size + 1}"
           )
                adapter.updateNotes(notes)
        }

    }
}

