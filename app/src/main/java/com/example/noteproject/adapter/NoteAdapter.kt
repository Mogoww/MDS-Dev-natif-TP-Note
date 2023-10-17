package com.example.noteproject.adapter

import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.noteproject.R
import com.example.noteproject.database.NoteDatabase
import com.example.noteproject.entities.Note
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NoteAdapter(private var notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var originalNotes: List<Note> = notes // Store the original list
    private var longPressedNotePosition: Int? = null

    // Interface for item click events
    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.note_title)
        val contentTextView: TextView = itemView.findViewById(R.id.note_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[holder.adapterPosition] // Use getAdapterPosition()

        holder.titleTextView.text = currentNote.title
        holder.contentTextView.text = currentNote.description

        // Handle item click event
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(currentNote)
        }

        // Handle long-press to show the delete dialog
        holder.itemView.setOnLongClickListener {
            longPressedNotePosition = holder.adapterPosition // Use getAdapterPosition()
            showDeleteDialog(holder.itemView.context)
            true
        }
    }


    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        originalNotes = newNotes
        notifyDataSetChanged()
    }

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

    fun showDeleteDialog(context: Context) {
        longPressedNotePosition?.let {
            val noteToDelete = notes[it]
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete Note")
            builder.setMessage("Are you sure you want to delete this note?")
            builder.setPositiveButton("Delete") { _, _ ->
                val newNotes = ArrayList(notes)
                newNotes.remove(noteToDelete)
                updateNotes(newNotes)

                GlobalScope.launch {
                    val noteDao = NoteDatabase.getDatabase(context)?.noteDao()
                    noteDao?.deleteNote(
                        noteToDelete
                    )
                }

                // Add code to delete the note from the database

                longPressedNotePosition = null
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val deleteDialog = builder.create()
            deleteDialog.show()
        }
    }
}
