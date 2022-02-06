package com.sumeet.cars360.ui.admin.fragments.bottom_navs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentAdminServiceLogsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminServiceLogsFragment : Fragment() {

    private lateinit var binding: FragmentAdminServiceLogsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminServiceLogsBinding.inflate(inflater,container,false)
        return binding.root
    }


}