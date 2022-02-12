package com.sumeet.cars360.ui.onboarding.fragments.customer

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.data.local.preferences.UserType
import com.sumeet.cars360.databinding.FragmentOTPBinding
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.ui.onboarding.AuthViewModel
import com.sumeet.cars360.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPFragment : Fragment() {
    private lateinit var binding: FragmentOTPBinding
    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var savePrefs: SavePrefs
    private var noOfTries: Int = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOTPBinding.inflate(
            LayoutInflater.from(context),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savePrefs = SavePrefs(requireContext())
        editTextInput()
        handleButton()
        observeData()
    }

    private fun observeData() {
        viewModel.currentUserOperation.observe(viewLifecycleOwner, Observer { it ->
            when (it) {
                is Resource.Success -> {
                    ViewVisibilityUtil.visibilityExchanger(binding.progressBar, binding.btnVerify)
                    it.data?.let { firebaseId ->

                        viewModel.findUserByFirebaseId(firebaseId)

                        viewModel.userDataFromServer.observe(viewLifecycleOwner, Observer { user ->
                            when (user) {
                                is Resource.Loading -> {}
                                is Resource.Error -> {
                                    //TODO handle it in better fashion
                                    Toast.makeText(
                                        context,
                                        "Sorry, we are facing some issues with the server",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                is Resource.Success -> {
                                    val userId = ReadPrefs(requireContext()).readUserId()
                                    if (user.data?.error == true && userId == "") {
                                        navigate(OTPFragmentDirections.actionOTPFragmentToNewCustomerDetailsFragment())
                                    } else {
                                        savePrefs.apply {
                                            saveLoginStatus(true)
                                            saveUserType(UserType.Customer)
                                        }
                                        startActivity(
                                            Intent(activity, CustomerActivity::class.java)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        ).also { activity?.finish() }
                                    }
                                }
                            }
                        })
                    }
                }
                is Resource.Loading -> {
                    ViewVisibilityUtil.visibilityExchanger(binding.progressBar, binding.llVerifyOtp)
                }
                is Resource.Error -> {
                    ViewVisibilityUtil.visibilityExchanger(binding.llVerifyOtp, binding.progressBar)
                    noOfTries--
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun handleButton() {
        binding.btnVerify.setOnClickListener {
            if (ButtonClickHandler.buttonClicked()) {
                if (checkValidity()) {
                    val code = binding.etC1.text.toString().trim() +
                            binding.etC2.text.toString().trim() +
                            binding.etC3.text.toString().trim() +
                            binding.etC4.text.toString().trim() +
                            binding.etC5.text.toString().trim() +
                            binding.etC6.text.toString().trim()

                    viewModel.crossCheckOTP(code)
                }
            }
        }
        binding.tvResendBtn.setOnClickListener {
            viewModel.resendOtp()
            Toast.makeText(context, "Re-sending OTP to your phone number", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkValidity(): Boolean {
        binding.apply {
            if (etC1.text.isNullOrEmpty()) {
                etC1.error = ""
                return false
            }
            if (etC2.text.isNullOrEmpty()) {
                etC2.error = ""
                return false
            }
            if (etC3.text.isNullOrEmpty()) {
                etC3.error = ""
                return false
            }
            if (etC4.text.isNullOrEmpty()) {
                etC4.error = ""
                return false
            }
            if (etC5.text.isNullOrEmpty()) {
                etC5.error = ""
                return false
            }
            if (etC6.text.isNullOrEmpty()) {
                etC6.error = ""
                return false
            }
        }
        return true
    }

    private fun editTextInput() {
        binding.etC1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC1.error = null
                if (s.isEmpty())
                    binding.etC1.requestFocus()
                else
                    binding.etC2.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC2.error = null
                if (s.isEmpty())
                    binding.etC1.requestFocus()
                else
                    binding.etC3.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC3.error = null
                if (s.isEmpty())
                    binding.etC2.requestFocus()
                else
                    binding.etC4.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC4.error = null
                if (s.isEmpty())
                    binding.etC3.requestFocus()
                else
                    binding.etC5.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC5.error = null
                if (s.isEmpty())
                    binding.etC4.requestFocus()
                else
                    binding.etC6.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC6.error = null
                if (s.isEmpty())
                    binding.etC5.requestFocus()
                else if (s.length == 1) {
                    hideVirtualKeyBoard(requireActivity(), requireContext())
                    binding.btnVerify.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
}