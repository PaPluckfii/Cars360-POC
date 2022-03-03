package com.sumeet.cars360.ui.admin.master_enquiry_and_complaint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentEnquiryAndComplaintBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnquiryAndComplaintFragment : Fragment() {

    private lateinit var binding: FragmentEnquiryAndComplaintBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnquiryAndComplaintBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO
    }

}