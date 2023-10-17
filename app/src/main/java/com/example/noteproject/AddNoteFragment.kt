import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.example.noteproject.Class.Note
import com.example.noteproject.R

class AddNoteFragment : Fragment() {

    private lateinit var editTextTitle: AppCompatEditText
    private lateinit var editTextDescription: AppCompatEditText
    private lateinit var buttonAddNote: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)

        editTextTitle = view.findViewById(R.id.editTextTitle)
        editTextDescription = view.findViewById(R.id.editTextDescription)
        buttonAddNote = view.findViewById(R.id.buttonAddNote)

        buttonAddNote.setOnClickListener {
            // Récupérez les valeurs de titre et de description depuis les champs EditText
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()

            // log the values
            Log.d("AddNoteFragment", "title: $title, description: $description")



            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}
