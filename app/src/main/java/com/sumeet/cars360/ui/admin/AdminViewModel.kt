package com.sumeet.cars360.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeet.cars360.data.remote.model.car_entities.CarEntitiesResponse
import com.sumeet.cars360.data.remote.model.service_logs.ServiceLogsByUserIdResponse
import com.sumeet.cars360.data.remote.model.user.UserResponse
import com.sumeet.cars360.data.remote.model.user.UsersByFirebaseIdResponse
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.Constants
import com.sumeet.cars360.util.Constants.NO_INTERNET_CONNECTION
import com.sumeet.cars360.util.Constants.hasInternetConnection
import com.sumeet.cars360.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _insertOperation = MutableLiveData<Resource<String>>()
    val insertOperation: LiveData<Resource<String>>
        get() = _insertOperation

    private val _carEntities = MutableLiveData<Resource<CarEntitiesResponse>>()
    val carEntities: LiveData<Resource<CarEntitiesResponse>>
        get() = _carEntities

    private val _allServiceLogs = MutableLiveData<Resource<ServiceLogsByUserIdResponse>>()
    val allServiceLogs:LiveData<Resource<ServiceLogsByUserIdResponse>> = _allServiceLogs

    private val _allCustomers = MutableLiveData<Resource<UsersByFirebaseIdResponse>>()
    val allCustomers:LiveData<Resource<UsersByFirebaseIdResponse>> = _allCustomers

    private val _allServiceAdvisors = MutableLiveData<Resource<UsersByFirebaseIdResponse>>()
    val allServiceAdvisors:LiveData<Resource<UsersByFirebaseIdResponse>> = _allServiceAdvisors

    fun getAllCarEntities() {
        _carEntities.postValue(Resource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getAllCarCollections()
                if (response.isSuccessful && response.body() != null)
                    if (response.body()?.error == false)
                        _carEntities.postValue(Resource.Success(response.body()))
                    else
                        _carEntities.postValue(Resource.Error(response.body()?.message))
                else
                    _carEntities.postValue(Resource.Error(response.message()))
            }else{
                _carEntities.postValue(Resource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }
    }


    fun insertNewCarBrand(name: String, logo: File) {
        _insertOperation.postValue(Resource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.addNewCarBrand(name, logo)
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()?.error == false)
                        _insertOperation.postValue(
                            Resource.Success(
                                response.body()?.brandInsertResponse?.get(
                                    0
                                )?.brandId
                            )
                        )
                    else
                        _insertOperation.postValue(Resource.Error(response.body()?.message))
                } else
                    _insertOperation.postValue(Resource.Error(response.message()))
            }else{
                _insertOperation.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    var currentBrandId: String = ""
    fun insertNewCarModel(name: String, logo: File) {
        _insertOperation.postValue(Resource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.addNewCarModel(currentBrandId, name, logo)
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()?.error == false)
                        _insertOperation.postValue(
                            Resource.Success(
                                response.body()?.modelInsertResponse?.get(
                                    0
                                )?.modelId
                            )
                        )
                    else
                        _insertOperation.postValue(Resource.Error(response.body()?.message))
                } else
                    _insertOperation.postValue(Resource.Error(response.message()))
            }else{
                _insertOperation.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

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

    fun resetUserDataInViewModel() {
        name = ""
        email = ""
        mobileNo = ""
        firebaseId = ""
        address = ""
        city = ""
        state = ""
        pinCode = ""
        dob = ""
        dom = ""
        gstIn = ""
        profileImage = null
    }

    fun insertNewUserData() {
        _insertOperation.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.IO) {
            if (hasInternetConnection()){
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
                    //TODO revert temp changes
                    if (response.body()?.error == true)
                        _insertOperation.postValue(
                            Resource.Error(response.body()?.message)
                        )
                    else {
                        val userId = response.body()?.userInsertResponse?.get(0)?.userId
                        _insertOperation.postValue(Resource.Success(userId))
                    }
                } else
                    _insertOperation.postValue(Resource.Error(response.message()))
            }else{
                _insertOperation.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    var modelId: String = ""
    var brandId: String = ""
    var vehicleNo: String = ""
    var bodyColor: String = ""
    var fuelType: String = ""
    var insuranceCompany: String = ""
    var insuranceExpiryDate: String = ""

    fun resetCarDataInViewModel() {
        modelId = ""
        brandId = ""
        vehicleNo = ""
        bodyColor = ""
        fuelType = ""
        insuranceCompany = ""
        insuranceExpiryDate = ""
    }

    fun insertNewCar(userId: String) {
        _insertOperation.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            if (hasInternetConnection()){
                val response = remoteRepository.addNewCarDetails(
                    userId,
                    modelId,
                    brandId,
                    vehicleNo,
                    bodyColor,
                    vehicleNo,
                    fuelType,
                    insuranceCompany,
                    insuranceExpiryDate,
                    userId
                )
                if (response.isSuccessful && response.body() != null) {
                    //TODO revert temp changes
                    if (response.body()?.error == true)
                        _insertOperation.postValue(
                            Resource.Error(
                                response.body()?.carInsertResponse?.get(
                                    0
                                )?.message
                            )
                        )
                    else {
                        val carId = response.body()?.carInsertResponse?.get(0)?.carId
                        _insertOperation.postValue(Resource.Success(carId))
                    }
                } else
                    _insertOperation.postValue(Resource.Error(response.message()))
            }else{
                _insertOperation.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    fun uploadCarHealthReport(serviceLogId: String, file: File) {
        //TODO
    }

    fun uploadServiceLogInvoice(serviceLogId: String, file: File) {
        //TODO
    }

    private val _allUsers = MutableLiveData<Resource<List<UserResponse>>>()
    val allUsers: LiveData<Resource<List<UserResponse>>>
        get() = _allUsers

    fun getAllUsers(){
        _allUsers.postValue(Resource.Loading())
        viewModelScope.launch {
            //TODO
        }
    }

    fun getAllServiceLogsByUserId(userId:String){
        _allServiceLogs.postValue(Resource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getAllServiceLogsByUserId(userId)

                if (response.isSuccessful && response.body() != null)
                    if (response.body()?.error == false)
                        _allServiceLogs.postValue(Resource.Success(response.body()))
                    else
                        _allServiceLogs.postValue(Resource.Error(response.body()?.message))
                else
                    _allServiceLogs.postValue(Resource.Error(response.message()))
            }else{
                _allServiceLogs.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    fun getAllCustomerByUserId(userId: String){
        _allCustomers.postValue(Resource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getUserByUserTypeId(userId)
                if (response.isSuccessful && response.body() != null)
                    if (response.body()?.error == false)
                        _allCustomers.postValue(Resource.Success(response.body()))
                    else
                        _allCustomers.postValue(Resource.Error(response.body()?.message))
                else
                    _allCustomers.postValue(Resource.Error(response.message()))
            }else{
                _allCustomers.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    fun getAllServiceAdvisorByUserId(userId: String){
        _allServiceAdvisors.postValue(Resource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getUserByUserTypeId(userId)
                if (response.isSuccessful && response.body() != null)
                    if (response.body()?.error == false)
                        _allServiceAdvisors.postValue(Resource.Success(response.body()))
                    else
                        _allServiceAdvisors.postValue(Resource.Error(response.body()?.message))
                else
                    _allServiceAdvisors.postValue(Resource.Error(response.message()))
            }else{
                _allServiceAdvisors.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

}