package com.example.noteproject

import DetailNoteFragment
import HomeNoteFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeNoteFragment = HomeNoteFragment()
        val detailNoteFragment = DetailNoteFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, homeNoteFragment)
            .commit()

    }
}

