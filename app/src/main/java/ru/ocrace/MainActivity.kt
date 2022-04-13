package ru.ocrace

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class MainActivity : FragmentActivity() {
    private lateinit var adapter: NumberAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTabs()
        //initPersonTab()


    }
    private fun initTabs(){
        val tabNameList = listOf<String>("RACE","PERSONS","STAGES","CHART","OBSTACLES")
        adapter = NumberAdapter(this)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNameList[position]
        }.attach()
    }
    private fun initPersonTab(){
        val personList: ListView = findViewById(R.id.person_list)
        var persons = mutableListOf<Person>()
        persons.add(0,Person(0,"Alexey","Potapov","Andreevich", "22.05.1984"))
        persons.add(1,Person(1,"Slava","Toldo","Andreevich", "22.05.1984"))
        val personStringList = listOf<String>(persons[0].toString(),persons[1].toString())
        val adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,personStringList)
        personList.adapter = adapter
    }
}