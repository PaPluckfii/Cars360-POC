package com.sumeet.cars360.ui.onboarding.fragments.customer

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.sumeet.cars360.data.local.preferences.CustomerLoginType
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.data.local.preferences.UserType
import com.sumeet.cars360.databinding.FragmentCustomerLoginBinding
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.ui.onboarding.AuthViewModel
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import com.sumeet.cars360.util.hideVirtualKeyBoard
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CustomerLoginFromProfile: Fragment() {

    private lateinit var binding: FragmentCustomerLoginBinding
    private val viewModel: AuthViewModel by activityViewModels()

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var verifyingMobileNumber = ""
    private var isMobileEnterScreen = true
    private var sentCode: String? = null
    private var resendToken:  PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var savePrefs: SavePrefs

    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            navigate()
//            Toast.makeText(context,"Verified",Toast.LENGTH_SHORT).show()
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(context, "Sorry, $e", Toast.LENGTH_SHORT).show()
            loadEnterMobileNumberScreen()
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            sentCode = verificationId
            resendToken = token
            loadOTPScreen()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savePrefs = SavePrefs(requireContext())
        setHasOptionsMenu(true)
        handleListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        navigate(CustomerLoginFromProfileDirections.actionCustomerLoginFromProfileToNavigationHome())
        return true
    }

    private fun handleListeners() {
        binding.apply {
            etNumber.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    tilNumber.error = null
                    if (p0?.length == 10)
                        binding.btnSend.requestFocus()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            btnSend.setOnClickListener {
                navigate(CustomerLoginFromProfileDirections.actionCustomerLoginFromProfileToNewCustomerFromProfile())
                if (isMobileEnterScreen && checkValidity()) {
                    ViewVisibilityUtil.visibilityExchanger(progressBar, clCustomerLogin)
                    verifyingMobileNumber = binding.etNumber.text.toString()
                    requestOtpForSignIn()
                    isMobileEnterScreen = false

//                    viewModel.requestOtp(etNumber.text.toString(), activity as Activity)
//                    SavePrefs(requireContext()).saveUserMobileNumber("+91"+etNumber.text.toString())
//
//                    OtpSentListener.addObservable()
//                    OtpSentListener._isOtpSent?.postValue(false)
//                    OtpSentListener._isOtpSent?.observe(viewLifecycleOwner) {
//                        if (it == true) {
//                            //TODO remove if not required
//                            navigate(CustomerLoginFragmentDirections.actionCustomerLoginFragmentToOTPFragment())
//                            OtpSentListener.removeObservable()
//                        }
//                    }
                }
                if (!isMobileEnterScreen) {
                    if (checkOTPValidity()) {
                        ViewVisibilityUtil.visibilityExchanger(progressBar, clCustomerLogin)
                        val code = binding.etC1.text.toString().trim() +
                                binding.etC2.text.toString().trim() +
                                binding.etC3.text.toString().trim() +
                                binding.etC4.text.toString().trim() +
                                binding.etC5.text.toString().trim() +
                                binding.etC6.text.toString().trim()

                        mAuth.signInWithCredential(
                            PhoneAuthProvider.getCredential(
                                sentCode!!,
                                code
                            )
                        )
                            .addOnCompleteListener {
                                checkUserDataInServer()
                            }
                    }
                }
            }

            tvResendBtn.setOnClickListener {
                resendOtp()
            }
        }
    }

    private fun resendOtp() {
        val options = resendToken?.let {
            PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber("+91$verifyingMobileNumber")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(mCallbacks)
                .setForceResendingToken(it)
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
            Toast.makeText(context,"Resending OTP",Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(context,"Something Wrong In resending OTP",Toast.LENGTH_SHORT).show()
    }

    private fun checkUserDataInServer() {

        mAuth.currentUser?.uid?.let {
            savePrefs.saveFirebaseId(it)
            viewModel.findUserByFirebaseId(it)
        }

        viewModel.userDataFromServer.observe(viewLifecycleOwner) { user ->
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
                    if (user.data?.error == true)
                        navigate(CustomerLoginFromProfileDirections.actionCustomerLoginFromProfileToNewCustomerFromProfile())
                    else {
                        savePrefs.apply {
                            saveLoginStatus(true)
                            saveUserType(UserType.Customer)
                            saveCustomerLoginType(CustomerLoginType.LoggedIn)
                        }
                        navigate(CustomerLoginFromProfileDirections.actionCustomerLoginFromProfileToNavigationProfile())
                    }
                }
            }
        }
    }

    private fun loadEnterMobileNumberScreen() {
        binding.apply {
            textViewHeading.text = "Please Enter Mobile Number"
            textViewSubHeading.text = "We will send an OTP to your entered mobile number."
            ViewVisibilityUtil.visibilityExchanger(tilNumber, llOtpFields)
            btnSend.text = "Send OTP"
            ViewVisibilityUtil.visibilityExchanger(clCustomerLogin, progressBar)
        }
    }

    private fun loadOTPScreen() {
        binding.apply {
            textViewHeading.text = "Verify OTP"
            textViewSubHeading.text = "Enter your received OTP here"
            ViewVisibilityUtil.visibilityExchanger(llOtpFields, tilNumber)
            btnSend.text = "Verify"
            ViewVisibilityUtil.visibilityExchanger(clCustomerLogin, progressBar)
            editTextInput()
        }
    }

    private fun requestOtpForSignIn() {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$verifyingMobileNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun checkValidity(): Boolean {
        return (binding.etNumber.text?.length == 10).also {
            if (!it)
                binding.tilNumber.error = "Invalid Mobile Number"
        }
    }

    private fun checkOTPValidity(): Boolean {
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
                    binding.btnSend.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }


}