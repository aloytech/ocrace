package ru.ocrace

data class Race (
    val rid: String? = null,
    val name: String? = null,
    val date: String? = null
        ) {
    override fun toString(): String {
        return "$rid $name $date"
    }
}