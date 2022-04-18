package ru.ocrace

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


const val ARG_PERSON = "person"

class PersonFragment : Fragment() {
    lateinit var buttonAccept: Button
    //firebase
    private val database = Firebase.database
    private val dbTablePersons = "Persons"
    private val dbTableSummary = "Summary"
    private var summary = Summary()

    //elements of PERSON tab
    private lateinit var inputName: EditText
    private lateinit var inputSurname: EditText
    private lateinit var inputMiddleName: EditText
    private lateinit var inputBirth: EditText
    private lateinit var inputSex: ToggleButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.persons_frag, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_PERSON) }?.apply {
        }
        initPersonTab(view)

    }

    private fun initPersonTab(view: View) {
        inputName = view.findViewById(R.id.input_name)
        inputSurname = view.findViewById(R.id.input_surname)
        inputMiddleName = view.findViewById(R.id.input_middle_name)
        inputBirth = view.findViewById(R.id.input_birth)
        inputSex = view.findViewById(R.id.sex_toggle)

        buttonAccept = view.findViewById(R.id.accept_button)
        buttonAccept.setOnClickListener(listener)

    }

    private val listener = View.OnClickListener {
        when (it.id) {
            R.id.accept_button -> {
                onClickAccept()
            }
        }
    }

    private fun onClickAccept() {
        val name = inputName.text.toString()
        val surname = inputSurname.text.toString()
        val secondName = inputMiddleName.text.toString()
        val birth = inputBirth.text.toString()
        val sex = inputSex.isChecked
        //database.reference.child(dbTableSummary).get().addOnSuccessListener { summary = it.value as Summary }

        writePerson(
            Person(
                name = name,
                surname = surname,
                secondName = secondName,
                birthDate = birth,
                isMale = sex
            )
        )
    }

    private fun writePerson(person: Person) {
        val dbRefPersons = database.getReference(dbTablePersons)


        val userId = dbRefPersons.push().key
        if (userId != null) {
            val pushPerson = person.copy(id = userId.toString())
            dbRefPersons.child(userId.toString()).setValue(pushPerson)
        } else {
            Log.w("add person", "bad push key")
        }
    }

}