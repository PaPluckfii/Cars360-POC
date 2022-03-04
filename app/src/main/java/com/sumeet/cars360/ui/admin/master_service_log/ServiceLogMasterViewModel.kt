package com.sumeet.cars360.ui.admin.master_service_log

import androidx.lifecycle.*
import com.sumeet.cars360.data.remote.request_data.ServiceLogFormData
import com.sumeet.cars360.data.repository.CacheRepository
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.FormDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceLogMasterViewModel @Inject constructor(
    private val cacheRepository: CacheRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    val customers = cacheRepository.getAllCustomers().asLiveData()

    fun getAllCarsByCustomerId(userId: String) =
        cacheRepository.getCarsByCustomerId(userId).asLiveData()

    private val _insertOperation = MutableLiveData<FormDataResource<String>>()
    val insertOperation: LiveData<FormDataResource<String>>
        get() = _insertOperation

    fun insertNewServiceLog(serviceLogFormData: ServiceLogFormData) {
        _insertOperation.postValue(FormDataResource.Loading())
        viewModelScope.launch {
            val response = remoteRepository.addNewServiceLog(serviceLogFormData)
            if (response.isSuccessful && response.body() != null) {
                if (response.body()?.error == true)
                    _insertOperation.postValue(FormDataResource.Error(response.body()?.message))
                else
                    _insertOperation.postValue(
                        FormDataResource.Success(
                            response.body()?.serviceLogInsertResponse?.get(
                                0
                            )?.carServiceId
                        )
                    )
            } else
                _insertOperation.postValue(FormDataResource.Error(response.message()))
        }
    }

    private val _carInsertOperation = MutableLiveData<FormDataResource<String>>()
    val carInsertOperation: LiveData<FormDataResource<String>>
        get() = _carInsertOperation

    fun insertNewCarIntoDatabase(
        userId: String,
        modelId: String,
        brandId: String,
        vehicleNo: String,
        bodyColor: String,
        fuelType: String,
        insuranceCompany: String,
        insuranceExpiryDate: String
    ) {
        _carInsertOperation.postValue(FormDataResource.Loading())
        viewModelScope.launch {
            val response = remoteRepository.addNewCarDetails(
                userId = userId,
                modelId = modelId,
                brandId = brandId,
                vehicleNo = vehicleNo,
                bodyColor = bodyColor,
                plateNo = "",
                fuelType = fuelType,
                insuranceCompany = insuranceCompany,
                insuranceExpiryDate = insuranceExpiryDate,
                createdBy = "2"
            )
            if (response.isSuccessful && response.body() != null) {
                if (response.body()?.error == true)
                    _carInsertOperation.postValue(FormDataResource.Error(response.body()?.message))
                else
                    _carInsertOperation.postValue(
                        FormDataResource.Success(
                            response.body()?.carInsertResponse?.get(
                                0
                            )?.carId
                        )
                    )
            } else
                _carInsertOperation.postValue(FormDataResource.Error(response.message()))
        }
    }

}