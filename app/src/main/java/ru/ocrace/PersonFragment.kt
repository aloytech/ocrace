package ru.ocrace

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


const val ARG_PERSON = "person"

class PersonFragment : Fragment() {

    //firebase
    private val database = Firebase.database
    private val dbTablePersons = "Persons"
    private val dbTableSummary = "Summary"
    private var summary: Summary? = null

    //elements of PERSON tab
    private lateinit var inputName: EditText
    private lateinit var inputSurname: EditText
    private lateinit var inputMiddleName: EditText
    private lateinit var inputBirth: EditText
    private lateinit var inputSex: ToggleButton

    private lateinit var listViewPersons: ListView

    private lateinit var buttonAccept: Button

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

        listViewPersons = view.findViewById(R.id.person_list)

        getUsersFromFB(view)
        getSummaryFromFB(view)

    }

    private fun getSummaryFromFB(view: View) {
        val summaryListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val indexLabel: TextView = view.findViewById(R.id.label_index)
                indexLabel.text = snapshot.child("indexPerson").value.toString()
                summary = snapshot.getValue(Summary::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FireBase", "Get from summary fail")
            }
        }
        val dbRefSummary = database.getReference(dbTableSummary)
        dbRefSummary.addValueEventListener(summaryListener)
    }

    private fun getUsersFromFB(view: View) {
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listPersons = mutableListOf<String>()
                for (user in snapshot.children) {
                    val person = user.getValue(Person::class.java)
                    listPersons.add(person.toString())
                }
                val listAdapter =
                    ArrayAdapter(view.context, android.R.layout.simple_list_item_1, listPersons)
                listViewPersons.adapter = listAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FireBase", "Get from persons fail")
            }
        }
        val dbChildPerson = database.getReference(dbTablePersons)
        dbChildPerson.addValueEventListener(userListener)
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

        writePerson(
            Person(
                name = name,
                surname = surname,
                secondName = secondName,
                birthDate = birth,
                male = sex
            )
        )
    }

    private fun writePerson(person: Person) {
        val dbRefPersons = database.getReference(dbTablePersons)
        val userId = dbRefPersons.push().key
        if (userId != null) {
            val userIndex = summary?.indexPerson
            if (userIndex != null) {
                summary?.indexPerson = userIndex + 1
                val dbRefSummary = database.getReference(dbTableSummary)
                dbRefSummary.child("indexPerson").setValue(userIndex + 1)
            }
            val pushPerson = person.copy(id = userIndex.toString())
            dbRefPersons.child(userIndex.toString()).setValue(pushPerson)
        } else {
            Log.w("FireBase", "Push to persons fail")
        }
    }

}