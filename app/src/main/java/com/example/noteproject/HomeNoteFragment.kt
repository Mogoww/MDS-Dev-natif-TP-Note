import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteproject.R
import com.example.noteproject.adapter.NoteAdapter
import com.example.noteproject.database.NoteDatabase
import com.example.noteproject.entities.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class HomeNoteFragment : Fragment() {
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_note, container, false)

        // Récupérer la RecyclerView depuis la mise en page du fragment
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Configurer le RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter with an empty list
        adapter = NoteAdapter(emptyList())
        recyclerView.adapter = adapter

        // Récupérer le SearchView depuis la mise en page du fragment
        val searchView: SearchView = view.findViewById(R.id.search_view)

        // Mettre en place le listener pour la recherche
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText)
                return true
            }
        })

        // Récupérez le bouton "Ajouter une note" depuis la mise en page
        val addNoteButton: FloatingActionButton = view.findViewById(R.id.add_note_button)

        // Ajoutez un OnClickListener pour ouvrir le detailNoteFragment
        addNoteButton.setOnClickListener {
            opendetailNoteFragment(null) // Pass null for noteId to create a new note
        }

        // Utilisez une coroutine pour charger les données depuis la base de données
        GlobalScope.launch {
            val noteDao = NoteDatabase.getDatabase(requireContext())?.noteDao()
            val notes_data_base = noteDao?.getAllNotes()
            Log.d("HomeNoteFragment", "notes_data_base: $notes_data_base")
            if (notes_data_base != null) {
                // Mettez à jour l'adaptateur sur le thread principal
                withContext(Dispatchers.Main) {
                    adapter.updateNotes(notes_data_base)
                }
            }
        }

        // Add a long-press handler for deleting a note
        recyclerView.setOnLongClickListener {
            adapter.showDeleteDialog(requireContext())
            true
        }

        // Ajoutez un gestionnaire de clic à l'adaptateur
        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                // Redirect to detailNoteFragment with the note's ID to update it
                opendetailNoteFragment(note.id!!.toLong())
            }
        })

        return view
    }

    private fun opendetailNoteFragment(noteId: Long?) {
        val detailNoteFragment = DetailNoteFragment()
        val args = Bundle()
        if (noteId != null) {
            args.putLong("note_id", noteId)
        }
        detailNoteFragment.arguments = args

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, detailNoteFragment)
            .addToBackStack(null)
            .commit()
    }
}
