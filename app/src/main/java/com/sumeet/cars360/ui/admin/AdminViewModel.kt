package com.sumeet.cars360.ui.admin

import androidx.lifecycle.*
import com.sumeet.cars360.data.remote.model.car_entities.CarEntitiesResponse
import com.sumeet.cars360.data.remote.model.service_logs.ServiceLogsByUserIdResponse
import com.sumeet.cars360.data.remote.model.user.UserResponse
import com.sumeet.cars360.data.remote.model.user.UsersByFirebaseIdResponse
import com.sumeet.cars360.data.repository.CacheRepository
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.Constants
import com.sumeet.cars360.util.Constants.NO_INTERNET_CONNECTION
import com.sumeet.cars360.util.Constants.hasInternetConnection
import com.sumeet.cars360.util.FormDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val cacheRepository: CacheRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    val carBrands = cacheRepository.getAllCarBrands().asLiveData()
    val customers = cacheRepository.getAllCustomers().asLiveData()
    val serviceAdvisors = cacheRepository.getAllServiceAdvisors().asLiveData()
    val serviceLogs = cacheRepository.getAllServiceLogs().asLiveData()
    val customerCars = cacheRepository.getAllCustomerCars().asLiveData()

    fun getCarModelByBrandId(brandId: String) = cacheRepository.getCarModelsByBrands(brandId).asLiveData()






    private val _insertOperation = MutableLiveData<FormDataResource<String>>()
    val insertOperation: LiveData<FormDataResource<String>>
        get() = _insertOperation

    private val _carEntities = MutableLiveData<FormDataResource<CarEntitiesResponse>>()
    val carEntities: LiveData<FormDataResource<CarEntitiesResponse>>
        get() = _carEntities

    private val _allServiceLogs = MutableLiveData<FormDataResource<ServiceLogsByUserIdResponse>>()
    val allServiceLogs:LiveData<FormDataResource<ServiceLogsByUserIdResponse>> = _allServiceLogs

    private val _allCustomers = MutableLiveData<FormDataResource<UsersByFirebaseIdResponse>>()
    val allCustomers:LiveData<FormDataResource<UsersByFirebaseIdResponse>> = _allCustomers

    private val _allServiceAdvisors = MutableLiveData<FormDataResource<UsersByFirebaseIdResponse>>()
    val allServiceAdvisors:LiveData<FormDataResource<UsersByFirebaseIdResponse>> = _allServiceAdvisors

    fun getAllCarEntities() {
        _carEntities.postValue(FormDataResource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getAllCarCollections()
                if (response.isSuccessful && response.body() != null)
                    if (response.body()?.error == false)
                        _carEntities.postValue(FormDataResource.Success(response.body()))
                    else
                        _carEntities.postValue(FormDataResource.Error(response.body()?.message))
                else
                    _carEntities.postValue(FormDataResource.Error(response.message()))
            }else{
                _carEntities.postValue(FormDataResource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }
    }


    fun insertNewCarBrand(name: String, logo: File) {
        _insertOperation.postValue(FormDataResource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.addNewCarBrand(name, logo)
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()?.error == false)
                        _insertOperation.postValue(
                            FormDataResource.Success(
                                response.body()?.brandInsertResponse?.get(
                                    0
                                )?.brandId
                            )
                        )
                    else
                        _insertOperation.postValue(FormDataResource.Error(response.body()?.message))
                } else
                    _insertOperation.postValue(FormDataResource.Error(response.message()))
            }else{
                _insertOperation.postValue(FormDataResource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    var currentBrandId: String = ""
    fun insertNewCarModel(name: String, logo: File) {
        _insertOperation.postValue(FormDataResource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.addNewCarModel(currentBrandId, name, logo)
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()?.error == false)
                        _insertOperation.postValue(
                            FormDataResource.Success(
                                response.body()?.modelInsertResponse?.get(
                                    0
                                )?.modelId
                            )
                        )
                    else
                        _insertOperation.postValue(FormDataResource.Error(response.body()?.message))
                } else
                    _insertOperation.postValue(FormDataResource.Error(response.message()))
            }else{
                _insertOperation.postValue(FormDataResource.Error(NO_INTERNET_CONNECTION))
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
        _insertOperation.postValue(FormDataResource.Loading())

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
                            FormDataResource.Error(response.body()?.message)
                        )
                    else {
                        val userId = response.body()?.userInsertResponse?.get(0)?.userId
                        _insertOperation.postValue(FormDataResource.Success(userId))
                    }
                } else
                    _insertOperation.postValue(FormDataResource.Error(response.message()))
            }else{
                _insertOperation.postValue(FormDataResource.Error(NO_INTERNET_CONNECTION))
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
        _insertOperation.postValue(FormDataResource.Loading())
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
                            FormDataResource.Error(
                                response.body()?.carInsertResponse?.get(
                                    0
                                )?.message
                            )
                        )
                    else {
                        val carId = response.body()?.carInsertResponse?.get(0)?.carId
                        _insertOperation.postValue(FormDataResource.Success(carId))
                    }
                } else
                    _insertOperation.postValue(FormDataResource.Error(response.message()))
            }else{
                _insertOperation.postValue(FormDataResource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    fun uploadCarHealthReport(serviceLogId: String, file: File) {
        //TODO
    }

    fun uploadServiceLogInvoice(serviceLogId: String, file: File) {
        //TODO
    }

    private val _allUsers = MutableLiveData<FormDataResource<List<UserResponse>>>()
    val allUsers: LiveData<FormDataResource<List<UserResponse>>>
        get() = _allUsers

    fun getAllUsers(){
        _allUsers.postValue(FormDataResource.Loading())
        viewModelScope.launch {
            //TODO
        }
    }

    fun getAllServiceLogsByUserId(userId:String){
        _allServiceLogs.postValue(FormDataResource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getAllServiceLogsByUserId(userId)

                if (response.isSuccessful && response.body() != null)
                    if (response.body()?.error == false)
                        _allServiceLogs.postValue(FormDataResource.Success(response.body()))
                    else
                        _allServiceLogs.postValue(FormDataResource.Error(response.body()?.message))
                else
                    _allServiceLogs.postValue(FormDataResource.Error(response.message()))
            }else{
                _allServiceLogs.postValue(FormDataResource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    fun getAllCustomerByUserId(userId: String){
        _allCustomers.postValue(FormDataResource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getUserByUserTypeId(userId)
                if (response.isSuccessful && response.body() != null)
                    if (response.body()?.error == false)
                        _allCustomers.postValue(FormDataResource.Success(response.body()))
                    else
                        _allCustomers.postValue(FormDataResource.Error(response.body()?.message))
                else
                    _allCustomers.postValue(FormDataResource.Error(response.message()))
            }else{
                _allCustomers.postValue(FormDataResource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    fun getAllServiceAdvisorByUserId(userId: String){
        _allServiceAdvisors.postValue(FormDataResource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getUserByUserTypeId(userId)
                if (response.isSuccessful && response.body() != null)
                    if (response.body()?.error == false)
                        _allServiceAdvisors.postValue(FormDataResource.Success(response.body()))
                    else
                        _allServiceAdvisors.postValue(FormDataResource.Error(response.body()?.message))
                else
                    _allServiceAdvisors.postValue(FormDataResource.Error(response.message()))
            }else{
                _allServiceAdvisors.postValue(FormDataResource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

}