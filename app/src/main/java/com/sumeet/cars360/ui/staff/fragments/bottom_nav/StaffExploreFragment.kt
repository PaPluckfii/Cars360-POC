package com.sumeet.cars360.ui.staff.fragments.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentCustomerExploreBinding
import com.sumeet.cars360.ui.customer.util.ExplorePagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffExploreFragment: Fragment() {

    private lateinit var binding: FragmentCustomerExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exploreViewPager.adapter = ExplorePagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.exploreViewPager) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.logo_cars360_symbol)
                }
                1 -> {
                    tab.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_vision)
                }
                2 -> {
                    tab.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_question)

                }
                else -> {
                    tab.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.logo_cars360_symbol)
                }
            }
        }.attach()

    }

}