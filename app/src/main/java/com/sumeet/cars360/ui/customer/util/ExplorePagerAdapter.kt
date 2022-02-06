package com.sumeet.cars360.ui.customer.util

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sumeet.cars360.ui.customer.fragments.explore_tabs.CompanyProfileFragment
import com.sumeet.cars360.ui.customer.fragments.explore_tabs.VisionAndMissionFragment
import com.sumeet.cars360.ui.customer.fragments.explore_tabs.WhyCars360Fragment

class ExplorePagerAdapter(fragment: Fragment):  FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CompanyProfileFragment()
            1 -> VisionAndMissionFragment()
            2 -> WhyCars360Fragment()
            else -> CompanyProfileFragment()
        }
    }
}