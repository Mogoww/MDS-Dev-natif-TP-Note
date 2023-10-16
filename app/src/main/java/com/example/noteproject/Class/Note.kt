package com.example.noteproject.Class

class Note(val title: String, val description: String) {
    override fun toString(): String {
        return "Notes(title='$title', description='$description')"
    }
}