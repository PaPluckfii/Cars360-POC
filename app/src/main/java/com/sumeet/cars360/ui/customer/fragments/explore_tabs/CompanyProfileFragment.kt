package com.sumeet.cars360.ui.customer.fragments.explore_tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentCompanyProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyProfileFragment : Fragment() {

    private lateinit var binding: FragmentCompanyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

}