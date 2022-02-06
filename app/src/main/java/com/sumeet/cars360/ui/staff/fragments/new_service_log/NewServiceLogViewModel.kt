package com.sumeet.cars360.ui.staff.fragments.new_service_log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class NewServiceLogViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
): ViewModel() {

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
    lateinit var leftPic: File
    lateinit var rightPic: File
    lateinit var backPic: File
    lateinit var frontPic: File

    fun insertServiceLog(){
        _insertOperation.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.IO) {
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

            if (response.isSuccessful && response.body() != null) {
                //TODO revert temp changes
                if (response.body()?.error == true)
                    _insertOperation.postValue(Resource.Success(""))
                //_userInsertOperation.postValue(Resource.Error(response.body()?.userInsertResponse?.get(0)?.message))
                else {
                    val userId = response.body()?.serviceLogInsertResponse?.get(0)?.carServiceId
                    _insertOperation.postValue(Resource.Success(userId))
                }
            } else
                _insertOperation.postValue(Resource.Error(response.message()))
        }
    }

}