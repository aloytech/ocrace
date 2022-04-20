package ru.ocrace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class NewraceFragment: Fragment() {

    //elements of NEWRACE tab
    private lateinit var inputRaceName: EditText
    private lateinit var inputRaceDate: EditText

    private lateinit var listViewRace: ListView

    private lateinit var buttonCreate: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.newrace_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(dbTableRaces) }?.apply {
        }
        initCreateRaceTab(view)
    }

    private fun initCreateRaceTab(view: View) {
        inputRaceName = view.findViewById(R.id.input_race_name)
        inputRaceDate = view.findViewById(R.id.input_race_date)

        listViewRace = view.findViewById(R.id.list_race)
        buttonCreate = view.findViewById(R.id.button_create)
        buttonCreate.setOnClickListener(listener)
        getRacesFromFB(view,listViewRace)
        getSummaryFromFB()
    }

    private val listener = View.OnClickListener {
        when (it.id) {
            R.id.button_create -> {
                onClickCreate()
            }
        }
    }

    private fun onClickCreate(){
        val name = inputRaceName.text.toString()
        val date = inputRaceDate.text.toString()
        writeRace(Race(name=name, date = date))
    }
    private fun writeRace(race: Race){
        val dbRefRaces = database.getReference(dbTableRaces)
        val raceId = dbRefRaces.push().key
        if (raceId != null) {
            val raceIndex = summary?.indexRace
            if (raceIndex != null) {
                summary?.indexRace = raceIndex + 1
                val dbRefSummary = database.getReference(dbTableSummary)
                dbRefSummary.child("indexRace").setValue(raceIndex + 1)
            }
            val pushRace = race.copy(rid = raceIndex.toString())
            dbRefRaces.child(raceIndex.toString()).setValue(pushRace)
        } else {
            Log.w("FireBase", "Push to races fail")
        }
    }
}