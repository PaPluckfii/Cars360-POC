package com.sumeet.cars360.ui.admin.fragments.service_log

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeet.cars360.data.remote.model.user.UsersByFirebaseIdResponse
import com.sumeet.cars360.data.remote.model.user_cars.CarDetailsResponseByUserId
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.Constants.NO_INTERNET_CONNECTION
import com.sumeet.cars360.util.Constants.hasInternetConnection
import com.sumeet.cars360.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ServiceLogMasterViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _insertOperation = MutableLiveData<Resource<String>>()
    val insertOperation: LiveData<Resource<String>>
        get() = _insertOperation

    var carId: String = ""
    var accessories: String = ""
    var serviceTypes: String = ""
    var estimates: String = ""
    var additionalDetails: String = ""
    var userCarRequests: String = ""
    var originalAmount: String = ""
    var estimatedAmount: String = ""
    var paidAmount: String = ""
    var paymentMode: String = ""
    var createdBy: String = ""
    var leftPic: File? = null
    var rightPic: File? = null
    var backPic: File? = null
    var frontPic: File? = null

    fun insertNewServiceLog() {
        _insertOperation.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = remoteRepository.addNewServiceLog(
                carId,
                accessories,
                serviceTypes,
                estimates,
                additionalDetails,
                userCarRequests,
                originalAmount,
                estimatedAmount,
                paidAmount,
                paymentMode,
                createdBy,
                leftPic,
                rightPic,
                frontPic,
                backPic
            )
            if(response.isSuccessful && response.body() != null){
                if(response.body()?.error == true)
                    _insertOperation.postValue(Resource.Error(response.body()?.message))
                else
                    _insertOperation.postValue(Resource.Success(response.body()?.serviceLogInsertResponse?.get(0)?.carServiceId))
            }
            else
                _insertOperation.postValue(Resource.Error(response.message()))
        }
    }

    private val _customerData = MutableLiveData<Resource<UsersByFirebaseIdResponse>>()
    val customerData: LiveData<Resource<UsersByFirebaseIdResponse>>
        get() = _customerData

    var customerCarData: CarDetailsResponseByUserId? = null

    fun searchUserByMobileNumber(mobileNo: String) {
        _customerData.postValue(Resource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getCarDetailsByMobileNumber(mobileNo)
                if(response.isSuccessful && response.body() != null && response.body()?.error==false){
                    val userId = response.body()?.carDetailsResponse?.get(0)?.userId
                    if(userId != null){
                        val userDataResponse = remoteRepository.getUserByUserId("11")
                        if(userDataResponse.isSuccessful && userDataResponse.body() != null){
                            if(userDataResponse.body()?.error == true) {
                                Log.d("USER_DATA",response.message())
                                _customerData.postValue(Resource.Error(userDataResponse.body()?.message))
                            }
                            else{
                                customerCarData = response.body()
                                _customerData.postValue(Resource.Success(userDataResponse.body()))
                            }
                        }
                    }
                    else
                        _customerData.postValue(Resource.Error(response.message()))
                }else{
                    _customerData.postValue(Resource.Error(NO_INTERNET_CONNECTION))
                }
            }
        }
    }
}