package com.example.noteproject

import AddNoteFragment
import HomeNoteFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteproject.adapter.NoteAdapter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeNoteFragment = HomeNoteFragment()
        val addNoteFragment = AddNoteFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, homeNoteFragment)
            .commit()

    }
}

