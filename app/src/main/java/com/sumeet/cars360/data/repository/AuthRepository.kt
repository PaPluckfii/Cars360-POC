package com.sumeet.cars360.data.repository

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.sumeet.cars360.util.Resource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val USER_DEFAULT_PASSWORD = "raipur-loves-cars360"

class AuthRepository @Inject constructor(){

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var verificationCode: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var userMobileNumber: String = ""
    private lateinit var authenticatingActivity: Activity

    private val _currentAuthUserOperation: MutableLiveData<Resource<String>> = MutableLiveData()
    val currentAuthUserOperation: LiveData<Resource<String>>
        get() = _currentAuthUserOperation

    private lateinit var credentials: PhoneAuthCredential

    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            _currentAuthUserOperation.postValue(Resource.Success(mAuth.currentUser?.uid))
        }

        override fun onVerificationFailed(e: FirebaseException) {
            _currentAuthUserOperation.postValue(Resource.Error(e.toString()))
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
//            OtpSentListener._isOtpSent?.postValue(true)
            verificationCode = verificationId
            resendToken = token
        }
    }

    fun requestOtpForSignIn(mobileNo: String, activity: Activity) {
        userMobileNumber = mobileNo
        authenticatingActivity = activity

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$userMobileNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(authenticatingActivity)
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun checkOTPAndSignIn(otp: String) {
        if (verificationCode != null) {
            _currentAuthUserOperation.postValue(Resource.Loading())
            credentials = PhoneAuthProvider.getCredential(verificationCode!!, otp)
            mAuth
                .signInWithCredential(credentials)
                .addOnCompleteListener {
                    _currentAuthUserOperation.postValue(Resource.Success(it.result?.user?.uid))
                }
        }
    }

    fun signInWithEmailAndPass(emailId: String, password: String) {
        _currentAuthUserOperation.postValue(Resource.Loading())
        mAuth
            .signInWithEmailAndPassword(emailId, password)
            .addOnSuccessListener {
                _currentAuthUserOperation.postValue(Resource.Success(mAuth.currentUser?.uid))
            }.addOnFailureListener {
                _currentAuthUserOperation.postValue(Resource.Error(it.toString()))
            }
    }

    fun resendOtp() {
        val options = resendToken?.let {
            PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber("+91$userMobileNumber")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(authenticatingActivity)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(it)
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        else
            Toast.makeText(authenticatingActivity.baseContext,"Something Wrong In resending OTP",Toast.LENGTH_LONG).show()
    }

}