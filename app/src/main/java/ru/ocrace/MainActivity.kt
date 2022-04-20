package ru.ocrace

import android.os.Bundle
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
val tabNameList = listOf("RACE", "NEWRACE", "PERSONS", "STAGES", "CHART", "OBSTACLES")
var summary: Summary? = null
var currentRace: Int = 0

class MainActivity : FragmentActivity() {
    //elements of tab system
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

        adapter = NumberAdapter(this)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNameList[position]
        }.attach()
    }
}

