import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteproject.Class.Note
import com.example.noteproject.R
import com.example.noteproject.adapter.NoteAdapter

class HomeNoteFragment : Fragment() {

    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_note, container, false)

        // Récupérer la RecyclerView depuis la mise en page du fragment
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Initialiser la liste de notes
        val notes = listOf(
            Note("title1", "description1"),
            Note("title2", "description2"),
            Note("title3", "description3")
        )

        // Initialiser l'adaptateur
        adapter = NoteAdapter(notes)

        // Configurer le RecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

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
        val addNoteButton: Button = view.findViewById(R.id.add_note_button)

        // Ajoutez un OnClickListener pour ouvrir le AddNoteFragment
        addNoteButton.setOnClickListener {
            openAddNoteFragment()
        }

        return view
    }

    private fun openAddNoteFragment() {
        // Créez une instance du AddNoteFragment
        val addNoteFragment = AddNoteFragment()

        // Remplacez le fragment actuel par le AddNoteFragment
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, addNoteFragment)
            .addToBackStack(null)
            .commit()
    }
}
