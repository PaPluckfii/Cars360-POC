package com.sumeet.cars360.ui.customer.fragments.explore_tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentWhyCars360Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhyCars360Fragment : Fragment() {

    private lateinit var binding: FragmentWhyCars360Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWhyCars360Binding.inflate(inflater,container,false)
        return binding.root
    }
}