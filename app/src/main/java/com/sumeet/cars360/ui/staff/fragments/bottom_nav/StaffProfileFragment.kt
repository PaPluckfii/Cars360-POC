package com.sumeet.cars360.ui.staff.fragments.bottom_nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentStaffProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffProfileFragment : Fragment() {

    private lateinit var binding: FragmentStaffProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

}