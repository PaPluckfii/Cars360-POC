package com.sumeet.cars360.ui.onboarding

import androidx.lifecycle.*
import com.sumeet.cars360.data.repository.CacheRepository
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.Constants
import com.sumeet.cars360.util.FormDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val cacheRepository: CacheRepository,
    private val remoteRepository: RemoteRepository
): ViewModel(){

    val carBrands = cacheRepository.getAllCarBrands().asLiveData()

    fun getCarModelByBrandId(brandId: String) = cacheRepository.getCarModelsByBrands(brandId).asLiveData()

    private val _insertOperation = MutableLiveData<FormDataResource<String>>()
    val insertOperation: LiveData<FormDataResource<String>>
        get() = _insertOperation

    var name: String = ""
    var email: String = ""
    var mobileNo: String = ""
    var firebaseId: String = ""
    var address: String = ""
    var city: String = ""
    var state: String = ""
    var pinCode: String = ""
    var dob: String = ""
    var dom: String = ""
    var gstIn: String = ""
    var profileImage: File? = null

    fun insertNewUserData() {
        _insertOperation.postValue(FormDataResource.Loading())

        viewModelScope.launch(Dispatchers.IO) {
            if (Constants.hasInternetConnection()) {
                val response = remoteRepository.addNewUserToServer(
                    name,
                    email,
                    mobileNo,
                    firebaseId,
                    address,
                    city,
                    state,
                    pinCode,
                    dob,
                    dom,
                    gstIn,
                    profileImage
                )
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()?.error == true)
                        _insertOperation.postValue(FormDataResource.Error(response.body()?.message))
                    else {
                        val userId = response.body()?.userInsertResponse?.get(0)?.userId
                        _insertOperation.postValue(FormDataResource.Success(userId))
                    }
                } else
                    _insertOperation.postValue(FormDataResource.Error(response.message()))
            } else {
                _insertOperation.postValue(FormDataResource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }
    }

}