package com.sumeet.cars360.ui.staff.fragments.bottom_nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentStaffServiceLogsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffServiceLogsFragment : Fragment() {

    private lateinit var binding: FragmentStaffServiceLogsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffServiceLogsBinding.inflate(inflater,container,false)
        return binding.root
    }

}