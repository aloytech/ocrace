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
fun getChildListFromFB(view: View, listView: ListView, dbIndex:String) {
    val childListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val list = mutableListOf<String>()
            for (child in snapshot.children) {
                list.add(child.key.toString())
            }
            val listAdapter =
                ArrayAdapter(view.context, R.layout.simple_list_item_1, list)
            listView.adapter = listAdapter
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("FireBase", "Get from db fail")
        }
    }
    val dbRef= database.getReference(dbIndex).child(dbTableStages)
    dbRef.addValueEventListener(childListener)
}
fun getUsersFromFB(view: View,listView: ListView) {
    val userListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val listPersons = mutableListOf<String>()
            for (user in snapshot.children) {
                val person = user.getValue(Person::class.java)
                listPersons.add(person.toString())
            }
            val listAdapter =
                ArrayAdapter(view.context, android.R.layout.simple_list_item_1, listPersons)
            listView.adapter = listAdapter
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("FireBase", "Get from persons fail")
        }
    }
    val dbChildPerson = database.getReference(dbTablePersons)
    dbChildPerson.addValueEventListener(userListener)
}