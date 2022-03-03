package com.sumeet.cars360.ui.admin.master_service_log.form_screens.part_6_finalize_and_upload

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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