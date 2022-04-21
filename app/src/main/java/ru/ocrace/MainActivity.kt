package ru.ocrace

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//firebase
val database = Firebase.database
const val dbTablePersons = "Persons"
const val dbTableSummary = "Summary"
const val dbTableRaces = "Races"
val tabNameList = listOf("RACE", "STAGES", "PERSONS","NEWRACE", "CHART", "OBSTACLES")
var summary: Summary? = null
var currentRace: Int? = null

class MainActivity : FragmentActivity() {
    //elements of tab system
    lateinit var currentRaceLabel: TextView
    private lateinit var adapter: NumberAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTabs()
        getSummaryFromFB()
    }

    private fun initTabs() {
        currentRaceLabel = findViewById(R.id.current_race_label)

        adapter = NumberAdapter(this)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNameList[position]
        }.attach()
    }
}

