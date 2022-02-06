package com.sumeet.cars360.ui.admin.fragments.service_log

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentServiceLogMasterFinalReviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterFinalReviewFragment : Fragment() {

    private lateinit var binding: FragmentServiceLogMasterFinalReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceLogMasterFinalReviewBinding.inflate(inflater,container,false)
        return binding.root
    }


}