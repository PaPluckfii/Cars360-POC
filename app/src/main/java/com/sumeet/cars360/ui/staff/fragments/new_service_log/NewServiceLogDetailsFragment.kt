package com.sumeet.cars360.ui.staff.fragments.new_service_log

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentNewServiceLogDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewServiceLogDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewServiceLogDetailsBinding
    private val viewModel: NewServiceLogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewServiceLogDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

}