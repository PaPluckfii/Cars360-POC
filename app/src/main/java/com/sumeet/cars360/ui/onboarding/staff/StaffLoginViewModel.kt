package com.sumeet.cars360.ui.onboarding.staff

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeet.cars360.data.remote.model.user.UsersByFirebaseIdResponse
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.Constants
import com.sumeet.cars360.util.FormDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffLoginViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
): ViewModel() {

    private val _userDataFromServer = MutableLiveData<FormDataResource<UsersByFirebaseIdResponse>>()
    val userDataFromServer: LiveData<FormDataResource<UsersByFirebaseIdResponse>>
        get() = _userDataFromServer

    fun findUserByFirebaseId(firebaseId: String) {
        viewModelScope.launch {
            _userDataFromServer.postValue(FormDataResource.Loading())
            if (Constants.hasInternetConnection()){
                val response = remoteRepository.getCustomerByFirebaseId(firebaseId)
                if (response.isSuccessful && response.body() != null) {
                    if(response.body()?.error == false)
                        _userDataFromServer.postValue(FormDataResource.Success(response.body()))
                    else
                        _userDataFromServer.postValue(FormDataResource.Success(response.body()))
                }
                else
                    _userDataFromServer.postValue(FormDataResource.Error(response.message()))
            }else{
                _userDataFromServer.postValue(FormDataResource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }
    }

}