package ru.ocrace

import java.util.*

data class Person(
    val id: Int,
    val name: String,
    val surname: String,
    val secondName: String,
    val birthDate: String,
    val isFemale: Boolean = false
) {
    override fun toString():String{
        val sex = if (isFemale){"F"} else {"M"}
        return "$id $surname $name $birthDate $sex"
    }
}