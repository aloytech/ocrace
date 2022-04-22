package ru.ocrace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment

class NewStageFragment: Fragment() {

    //elements of NEWSTAGE tab
    private lateinit var inputStageName: EditText

    private lateinit var listViewStage: ListView

    private lateinit var buttonCreateStage: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.newstage_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(dbTableStages) }?.apply {
        }
        initCreateStageTab(view)
    }

    private fun initCreateStageTab(view: View) {
        inputStageName = view.findViewById(R.id.input_stage_name)

        listViewStage = view.findViewById(R.id.list_stage)
        buttonCreateStage = view.findViewById(R.id.button_create_stage)
        buttonCreateStage.setOnClickListener(listener)
        if (currentRace != null) {
            getChildListFromFB(view, listViewStage, currentRace.toString())
        } else {
            Log.w("CurrentRace", "Race Not Selected")
            Toast.makeText(this.context,"Race not selected",Toast.LENGTH_SHORT).show()
        }
    }

    private val listener = View.OnClickListener {
        when (it.id) {
            R.id.button_create_stage -> {
                val stage = inputStageName.text.toString()
                if (stage.isEmpty()){
                    Toast.makeText(this.context,"Empty field",Toast.LENGTH_SHORT).show()
                } else {
                    writeStage(stage)
                }
            }
        }
    }

    private fun writeStage(stage: String){

        if (currentRace != null) {
            val dbRefCurrentRace = database.getReference(currentRace.toString())
            dbRefCurrentRace.child(dbTableStages).child(stage).setValue(stage)
        } else {
            Log.w("CurrentRace", "Race Not Selected")
            Toast.makeText(this.context,"Race not selected",Toast.LENGTH_SHORT).show()
        }
    }
}