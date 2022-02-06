package com.sumeet.cars360.ui.admin.fragments.service_log

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.sumeet.cars360.databinding.FragmentServiceLogDetailsBinding

class ServiceLogDetailsFragment : Fragment() {

    private val args:ServiceLogDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentServiceLogDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceLogDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.let {

        }
    }
}