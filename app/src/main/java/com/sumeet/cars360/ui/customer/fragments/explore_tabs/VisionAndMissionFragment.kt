package com.sumeet.cars360.ui.customer.fragments.explore_tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentVisionAndMissionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VisionAndMissionFragment : Fragment() {

    private lateinit var binding: FragmentVisionAndMissionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVisionAndMissionBinding.inflate(inflater,container,false)
        return binding.root
    }

}