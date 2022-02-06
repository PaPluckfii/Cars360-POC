package com.sumeet.cars360.ui.onboarding.fragments.customer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.databinding.FragmentCustomerLoginBinding
import com.sumeet.cars360.ui.admin.AdminActivity
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.ui.onboarding.AuthViewModel
import com.sumeet.cars360.ui.staff.StaffActivity
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.ViewVisibilityUtil
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerLoginFragment : Fragment() {

    private lateinit var binding: FragmentCustomerLoginBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
    }

    private fun handleListeners() {
        binding.apply {

            //TODO remove
            btnCustomer.setOnClickListener {
                startActivity(
                    Intent(activity, CustomerActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ).also {
                    activity?.finish()
                }
            }
            btnAdmin.setOnClickListener {
                startActivity(
                    Intent(activity, AdminActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ).also {
                    activity?.finish()
                }
            }
//            btnStaff.setOnClickListener {
//                startActivity(
//                    Intent(activity, StaffActivity::class.java)
//                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                ).also {
//                    activity?.finish()
//                }
//            }


            etPhone.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    etPhone.error = null
                    if(p0?.length == 10)
                        binding.btnSend.requestFocus()
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })
            tvClickHere.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()){
                    navigate(CustomerLoginFragmentDirections.actionCustomerLoginFragmentToStaffLoginFragment())
                }
            }
            btnSend.setOnClickListener {
                if (checkValidity()) {

                    //TODO remove if not required
                        SavePrefs(requireContext()).saveUserMobileNumber("+91"+etPhone.text.toString())

                    ViewVisibilityUtil.visibilityExchanger(progressBar,clCustomerLogin)
                    viewModel.requestOtp(etPhone.text.toString(), activity as Activity)
                    viewModel.readyToNavigate.observe(viewLifecycleOwner, {
                        if (it == true) {
                            navigate(CustomerLoginFragmentDirections.actionCustomerLoginFragmentToOTPFragment())
                        }
                    })
                }
            }
        }
    }

    private fun checkValidity(): Boolean {
        return (binding.etPhone.text.length == 10).also {
            if(!it)
                binding.etPhone.error = "Invalid Mobile Number"
        }
    }

}