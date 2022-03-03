package com.sumeet.cars360.ui.admin.data_presenter.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sumeet.cars360.databinding.FragmentAdminStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminStatisticsFragment : Fragment() {

    private lateinit var binding: FragmentAdminStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

}