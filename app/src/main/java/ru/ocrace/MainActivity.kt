package ru.ocrace

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class MainActivity : FragmentActivity() {
    //elements of tab system
    private lateinit var adapter: NumberAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    //elements of PERSON tab
    //private lateinit var inputName: EditText
    //private lateinit var inputSurname: EditText
    //private lateinit var inputSecondName: EditText
    //private lateinit var inputBirth: EditText
    //@SuppressLint("UseSwitchCompatOrMaterialCode")
    //private lateinit var inputSex: Switch
    private lateinit var buttonAccept: Button

    //firebase
    private val database = Firebase.database


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
        //inputName = findViewById(R.id.input_name)
        //inputSurname = findViewById(R.id.input_surname)
        //inputSecondName = findViewById(R.id.input_second_name)
        //inputBirth = findViewById(R.id.input_birth)
        //inputSex = findViewById(R.id.switch_sex)

        buttonAccept = findViewById(R.id.accept_button)
        //buttonAccept.setOnClickListener(listener)
    }
    private val listener = View.OnClickListener {
        when (it.id){
            R.id.accept_button ->{
                //onClickAccept()
            }
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    fun onClickAccept(view: View){
        //val name = inputName.text.toString()
        val name:EditText = findViewById(R.id.input_name)
        //val surname = inputSurname.text.toString()
        //val secondName = inputSecondName.text.toString()
        //val birth = inputBirth.text.toString()
        //val sex = inputSex.isChecked
        val surname : EditText = findViewById(R.id.input_surname)
        val secondName: EditText = findViewById(R.id.input_second_name)
        val birth: EditText = findViewById(R.id.input_birth)
        //val sex:Switch = findViewById(R.id.switch_sex)
        writePerson(Person(
            name = name.text.toString(),
            surname = surname.text.toString(),
            secondName = secondName.text.toString(),
            birthDate = birth.text.toString(),
            isFemale = false
        ))
    }
    private fun writePerson(person: Person){
        val dbRef = database.getReference(R.string.db_table_persons.toString())

        val userId = dbRef.push().key
        if (userId != null) {
            val pushPerson = Person(
                userId,person.name,person.surname,person.secondName,person.birthDate,person.isFemale)
            dbRef.child(userId).setValue(pushPerson)
        } else{
            Log.w("add person", "bad push key")
        }
    }

}