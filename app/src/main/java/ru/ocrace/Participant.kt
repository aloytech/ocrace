package ru.ocrace

import java.text.SimpleDateFormat
import java.util.*

data class Participant(
    val raceId: String? = null,
    val number: String? = null,
    val stage: String? = null,
    val start: String? = null,
    val finish: String? = null,
    var totalTimeSec: Int? = null,
    val personId: Int? = null,
    val shortName: String? = null,
    val faults: List<Int>? = null,
    val faultsCount: Int? = null
):Comparable<Participant> {
    override fun toString():String{
        val run = if (finish==null||start==null) {
            "nfy"
        }else {
            val pattern = SimpleDateFormat("HH:mm:ss", Locale.US)
            val startTime = pattern.parse(start)
            val finishTime = pattern.parse(finish)
            if (finishTime != null&&startTime != null) {
                totalTimeSec = ((finishTime.time - startTime.time)/1000).toInt()
            }

            "$finish"
        }
        val str = if (start==null) {"nsy"}else{"$start"}
        val faultsOut = if (faults==null) {"no faults yet"}else{"$faults"}
        return "$number $shortName $str $run $faultsOut"
    }

    override fun compareTo(other: Participant): Int {
        TODO("Not yet implemented")
    }

}