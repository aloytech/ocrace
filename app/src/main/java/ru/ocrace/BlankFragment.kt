package ru.ocrace

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toolbar


const val ARG_BLANK = "blank"

class BlankFragment : Fragment() {


    private lateinit var listRaceSelect: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_BLANK) }?.apply {
        }
        initSelectRaceTab(view)
    }
    private fun initSelectRaceTab(view: View){

        listRaceSelect = view.findViewById(R.id.list_race_select)
        getRacesFromFB(view,listRaceSelect)
        listRaceSelect.onItemClickListener = itemClickListener
    }

    private val itemClickListener = AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
        val itemValue = p0.getItemAtPosition(p2) as String
        val currentRaceLabel = activity?.findViewById<TextView>(R.id.current_race_label)
        if (currentRaceLabel != null) {
            currentRaceLabel.text = itemValue
        }
        currentRace=itemValue.substring(0..1).trim().toInt()
    }

}