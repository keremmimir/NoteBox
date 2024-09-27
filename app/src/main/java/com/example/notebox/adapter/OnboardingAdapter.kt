package com.example.notebox.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notebox.ui.onboard.OnBoardingFragment1
import com.example.notebox.ui.onboard.OnBoardingFragment2
import com.example.notebox.ui.onboard.OnBoardingFragment3

class OnboardingAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardingFragment1()
            1 -> OnBoardingFragment2()
            2 -> OnBoardingFragment3()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}