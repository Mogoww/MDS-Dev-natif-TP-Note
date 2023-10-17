import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.example.noteproject.R
import com.example.noteproject.database.NoteDatabase
import com.example.noteproject.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext




class DetailNoteFragment : Fragment() {
    private lateinit var editTextTitle: AppCompatEditText
    private lateinit var editTextDescription: AppCompatEditText
    private lateinit var buttonAddNote: AppCompatButton
    private var noteId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)

        editTextTitle = view.findViewById(R.id.editTextTitle)
        editTextDescription = view.findViewById(R.id.editTextDescription)
        buttonAddNote = view.findViewById(R.id.buttonAddNote)

        // Vérifiez si un ID de note est passé en argument
        val arguments = arguments
        if (arguments != null && arguments.containsKey("note_id")) {
            noteId = arguments.getLong("note_id")
        }

        // Si un ID de note est présent, essayez de récupérer la note de la base de données
        if (noteId != null) {
            GlobalScope.launch {
                val noteDao = NoteDatabase.getDatabase(requireContext())?.noteDao()
                val note = noteDao?.getNoteById(noteId!!.toInt())
                withContext(Dispatchers.Main) {
                    if (note != null) {
                        // Remplissez les champs d'édition avec les données de la note existante
                        editTextTitle.setText(note.title)
                        editTextDescription.setText(note.description)
                    }
                }
            }
        }

        buttonAddNote.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()

            if (title.isEmpty()) {
                editTextTitle.error = "Title required"
                return@setOnClickListener
            }

            if (noteId != null) {
                // Mettez à jour la note existante
                GlobalScope.launch {
                    val noteDao = NoteDatabase.getDatabase(requireContext())?.noteDao()
                    noteDao?.updateNote(Note(id = noteId!!.toInt(), title = title, description = description))
                }
            } else {
                // Créez une nouvelle note
                GlobalScope.launch {
                    val noteDao = NoteDatabase.getDatabase(requireContext())?.noteDao()
                    noteDao?.insertNotes(Note(title, description))
                }
            }

            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}

