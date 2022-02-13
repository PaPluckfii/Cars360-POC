package com.sumeet.cars360.ui.onboarding.fragments.customer

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.databinding.FragmentCustomerLoginProfileBinding
import com.sumeet.cars360.ui.onboarding.AuthViewModel
import com.sumeet.cars360.ui.onboarding.fragments.new_customer.NewCustomerViewModel
import com.sumeet.cars360.util.ViewVisibilityUtil
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerLoginProfileFragment : Fragment() {

    private lateinit var binding: FragmentCustomerLoginProfileBinding
    private val viewModel: AuthViewModel by activityViewModels()
    private val newCustomerViewModel:NewCustomerViewModel by activityViewModels()
    private lateinit var savePrefs: SavePrefs
    private lateinit var readPrefs: ReadPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savePrefs = SavePrefs(requireContext())
        readPrefs = ReadPrefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (readPrefs.readLoginStatus()){
            navigate(CustomerLoginProfileFragmentDirections.actionCustomerLoginProfileFragmentToNavigationProfile2())
        }
        binding = FragmentCustomerLoginProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
    }

    private fun handleListeners() {
        binding.apply {
            etPhone.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    etPhone.error = null
                    if(p0?.length == 10)
                        binding.btnSend.requestFocus()
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    tilName.error = null
                }

                override fun afterTextChanged(s: Editable) {}
            })

            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    tilEmailId.error = null
                }

                override fun afterTextChanged(s: Editable) {}
            })

            btnSend.setOnClickListener {

                if (checkDataValidity()) {
                    //TODO remove if not required
                    savePrefs.saveUserMobileNumber("+91"+etPhone.text.toString())
                    newCustomerViewModel.apply {
                        name = etName.text.toString()
                        mobileNo = "+91"+etPhone.text.toString()
                        email = etEmail.text.toString()
                    }

                    newCustomerViewModel.insertNewUserData()

                    ViewVisibilityUtil.visibilityExchanger(progressBar,clCustomerLogin)
                    viewModel.requestOtp(etPhone.text.toString(), activity as Activity)
                    viewModel.readyToNavigate.observe(viewLifecycleOwner, Observer {
                        if (it == true) {
                            navigate(CustomerLoginProfileFragmentDirections.actionCustomerLoginProfileFragmentToOTPFragment2())
                        }
                    })
                }
            }

        }
    }

    private fun checkDataValidity(): Boolean {
        var validity = true
        if (binding.etPhone.text.length !=10){
            binding.etPhone.error = "Invalid Mobile Number"
        }
        if (binding.etName.text.isNullOrEmpty()) {
            binding.tilName.error = "Name Field Cannot be Empty"
            validity = false
        }
        if (binding.etEmail.text.isNullOrEmpty()) {
            binding.tilEmailId.error = "Email Cannot be Empty"
            validity = false
        }
        return validity
    }


}