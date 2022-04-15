package ru.ocrace

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NumberAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> {
                val fragment = PersonFragment()

                fragment.arguments = Bundle().apply {
                    putInt(ARG_PERSON, position + 1)
                    return fragment
                }
            }
            else -> {
                val fragment = BlankFragment()

                fragment.arguments = Bundle().apply {
                    putInt(ARG_BLANK, position + 1)
                    return fragment
                }
            }
        }
    }
}