package ru.ocrace


data class Person(
    val id: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val secondName: String? = null,
    val birthDate: String? = null,
    val male: Boolean? = null
) {
    override fun toString(): String {
        val sex = if (male == true) {
            "M"
        } else {
            "F"
        }
        return "$id $surname $name $birthDate $sex"
    }
}