package com.example.noteproject.adapter

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteproject.R
import com.example.noteproject.entities.Note

class NoteAdapter(private var notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var originalNotes: List<Note> = notes // Stockez la liste originale

    // Créez une classe interne pour contenir les éléments de la vue
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.note_title)
        val contentTextView: TextView = itemView.findViewById(R.id.note_content)
    }

    // Créez une vue pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    // Remplissez les données de la vue
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.titleTextView.text = currentNote.title
        holder.contentTextView.text = currentNote.description
    }

    // Retourne le nombre d'éléments dans la liste
    override fun getItemCount(): Int {
        return notes.size
    }

    // Mettez à jour la liste de notes et la liste originale
    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        originalNotes = newNotes
        notifyDataSetChanged()
    }

    // Filtrer les notes en fonction du texte de recherche
    fun filter(query: String?) {
        if (query.isNullOrBlank()) {
            notes = originalNotes
        } else {
            notes = originalNotes.filter {
                it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}