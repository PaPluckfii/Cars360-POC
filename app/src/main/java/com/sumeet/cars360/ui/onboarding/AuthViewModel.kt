package com.sumeet.cars360.ui.onboarding

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeet.cars360.data.remote.model.user.UsersByFirebaseIdResponse
import com.sumeet.cars360.data.repository.AuthRepository
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.Constants.NO_INTERNET_CONNECTION
import com.sumeet.cars360.util.Constants.hasInternetConnection
import com.sumeet.cars360.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _readyToNavigate: MutableLiveData<Boolean> = MutableLiveData()
    val readyToNavigate: LiveData<Boolean>
        get() = _readyToNavigate

    fun requestOtp(mobileNo: String, activity: Activity) {
        authRepository.requestOtpForSignIn(mobileNo, activity)
        _readyToNavigate.postValue(true)
    }

    fun resendOtp(){
        authRepository.resendOtp()
    }

    fun crossCheckOTP(otp: String) {
        authRepository.checkOTPAndSignIn(otp)
    }

    val currentUserOperation = authRepository.currentAuthUserOperation

    fun signInStaff(emailId: String, password: String) {
        _readyToNavigate.postValue(false)
        authRepository.signInWithEmailAndPass(emailId, password)
    }

    private val _userDataFromServer = MutableLiveData<Resource<UsersByFirebaseIdResponse>>()
    val userDataFromServer: LiveData<Resource<UsersByFirebaseIdResponse>>
        get() = _userDataFromServer

    fun findUserByFirebaseId(firebaseId: String) {
        viewModelScope.launch {
            _userDataFromServer.postValue(Resource.Loading())
            if (hasInternetConnection()){
                val response = remoteRepository.getCustomerByUserId(firebaseId)
                if (response.isSuccessful && response.body() != null) {
                    if(response.body()?.error == false)
                        _userDataFromServer.postValue(Resource.Success(response.body()))
                    else
                        _userDataFromServer.postValue(Resource.Success(response.body()))
                }
                else
                    _userDataFromServer.postValue(Resource.Error(response.message()))
            }else{
                _userDataFromServer.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

}