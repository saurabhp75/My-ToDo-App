package com.ytlabs.mytodoapp.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                OnBoardingOneFragment()
            }
            1 -> {
                OnBoardingTwoFragment()
            }
            else -> {
                throw IllegalStateException("Not Handled Position $position")
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

}