package com.sumeet.cars360.ui.admin.master_service_log

import androidx.lifecycle.*
import com.sumeet.cars360.data.remote.form_data.ServiceLogFormData
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

    fun getAllCarsByCustomerId(userId: String) = cacheRepository.getCarsByCustomerId(userId).asLiveData()

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

}