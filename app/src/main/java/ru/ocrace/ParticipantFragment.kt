package ru.ocrace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class ParticipantFragment: Fragment() {

    private lateinit var listStageSelect: ListView
    private lateinit var listPersonSelect: ListView
    private lateinit var viewStage: TextView
    private lateinit var viewNumberAssignment: TextView
    private lateinit var buttonConfirm: Button

    private lateinit var inputNumber: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.participants_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(dbTableParticipants) }?.apply {
        }
        initParticipantTab(view)

    }
    private fun initParticipantTab(view: View){
        listPersonSelect = view.findViewById(R.id.list_person_select)
        listStageSelect = view.findViewById(R.id.list_stage_select)
        viewStage = view.findViewById(R.id.view_stage_assignment)
        viewNumberAssignment = view.findViewById(R.id.view_number_assignment)
        buttonConfirm = view.findViewById(R.id.button_confirm)
        buttonConfirm.setOnClickListener(listener)

        inputNumber = view.findViewById(R.id.input_number)

        getUsersFromFB(view,listPersonSelect)
        if (currentRace != null) {
            getChildListFromFB(view, listStageSelect, currentRace.toString())
        } else {
            Log.w("CurrentRace", "Race Not Selected")
            Toast.makeText(this.context,"Race not selected", Toast.LENGTH_SHORT).show()
        }
        listStageSelect.onItemClickListener = stageClickListener
        listPersonSelect.onItemClickListener = personClickListener
    }

    private val listener = View.OnClickListener {
        when (it.id) {
            R.id.button_confirm -> {
                val participant = viewNumberAssignment.text.toString()
                val stage = viewStage.text.toString()
                if (participant.isEmpty()||stage.isEmpty()){
                    Toast.makeText(this.context,"Empty field",Toast.LENGTH_SHORT).show()
                } else {
                    writeParticipant(inputNumber.text.toString(),participant,stage)
                }
            }
        }
    }
    private fun writeParticipant(number: String, person: String,stage: String){
        if (currentRace != null) {
            val dbRefCurrentRace = database.getReference(currentRace.toString())
            val shortName = person.split(" ")[1] + person.split(" ")[2][0]
            val newParticipant = Participant(
                currentRace.toString(),
                number,
                stage,
                "10:00:00",
                "10:25:43",
                451,
                person.split(" ")[0].toInt(),
                shortName,
                listOf(2,6,8),
                3
            )
            dbRefCurrentRace.child(dbTableParticipants).child(number).setValue(newParticipant)
        } else {
            Log.w("Participants", "Race Not Selected")
            Toast.makeText(this.context,"Race not selected",Toast.LENGTH_SHORT).show()
        }
    }

    private val stageClickListener = AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
        val itemValue = p0.getItemAtPosition(p2) as String
        viewStage.text = itemValue
    }
    private val personClickListener = AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
        val itemValue = p0.getItemAtPosition(p2) as String
        viewNumberAssignment.text = itemValue
    }
}