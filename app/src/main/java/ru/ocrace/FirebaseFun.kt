package ru.ocrace

import android.R
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

fun getSummaryFromFB() {
    val summaryListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            summary = snapshot.getValue(Summary::class.java)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("FireBase", "Get from summary fail")
        }
    }
    val dbRefSummary = database.getReference(dbTableSummary)
    dbRefSummary.addValueEventListener(summaryListener)
}
fun getRacesFromFB(view: View, listView: ListView) {
    val raceListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val listRace = mutableListOf<String>()
            for (raceSnap in snapshot.children) {
                val race = raceSnap.getValue(Race::class.java)
                listRace.add(race.toString())
            }
            val listAdapter =
                ArrayAdapter(view.context, R.layout.simple_list_item_1, listRace)
            listView.adapter = listAdapter
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("FireBase", "Get from race fail")
        }
    }
    val dbRefRaces = database.getReference(dbTableRaces)
    dbRefRaces.addValueEventListener(raceListener)
}