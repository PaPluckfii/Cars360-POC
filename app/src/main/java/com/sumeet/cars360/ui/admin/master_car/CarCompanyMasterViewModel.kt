package com.sumeet.cars360.ui.admin.master_car

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeet.cars360.data.local.room.model.CarModelEntity
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.FormDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CarCompanyMasterViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
): ViewModel(){

    private val _insertOperation = MutableLiveData<FormDataResource<String>>()
    val insertOperation: LiveData<FormDataResource<String>>
        get() = _insertOperation

    fun updateCarModel(modelEntity: CarModelEntity, logoFile: File){
        _insertOperation.postValue(FormDataResource.Loading())

        viewModelScope.launch {
            val response =
                remoteRepository.updateExistingModel(
                    modelId = modelEntity.modelId,
                    brandId = modelEntity.brandId!!,
                    modelName = modelEntity.modelName.toString(),
                    modelLogo = logoFile
                )
            if(response.isSuccessful && response.body() != null) {
                _insertOperation.postValue(FormDataResource.Success("${modelEntity.modelName} has been update in the server"))
            }
            else
                _insertOperation.postValue(FormDataResource.Error(response.message()))
        }

    }

}