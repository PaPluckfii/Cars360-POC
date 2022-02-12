package com.sumeet.cars360.ui.staff

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeet.cars360.data.local.room.model.UserEntity
import com.sumeet.cars360.data.remote.model.car_entities.CarEntitiesResponse
import com.sumeet.cars360.data.remote.old_model.Galleries
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.data.repository.RoomRepository
import com.sumeet.cars360.util.Constants.NO_INTERNET_CONNECTION
import com.sumeet.cars360.util.Constants.hasInternetConnection
import com.sumeet.cars360.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val remoteRepository: RemoteRepository
): ViewModel(){

    private val _myUser = MutableLiveData<UserEntity>()
    val myUser: LiveData<UserEntity>
        get() = _myUser

    private val _carEntities = MutableLiveData<Resource<CarEntitiesResponse>>()
    val carEntities: LiveData<Resource<CarEntitiesResponse>>
        get() = _carEntities

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
                _carEntities.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    fun getGalleriesData(): List<Galleries>{
        //TODO get From Repo
        return listOf(
            Galleries(
                "https://cars-360.in/uploads/gallery/1539664311DSC_0661.JPG",
                "https://cars-360.in/uploads/gallery/r_1539664311DSC_0658.JPG",
                "https://cars-360.in/uploads/gallery/1539664311DSC_0659.JPG",
                "https://cars-360.in/uploads/gallery/r_15396643121DSC_0664.JPG"
            ),
            Galleries(
                "https://cars-360.in/uploads/gallery/1539664311DSC_0661.JPG",
                "https://cars-360.in/uploads/gallery/r_1539664311DSC_0658.JPG",
                "https://cars-360.in/uploads/gallery/1539664311DSC_0659.JPG",
                "https://cars-360.in/uploads/gallery/r_15396643121DSC_0664.JPG"
            ),
            Galleries(
                "https://cars-360.in/uploads/gallery/1539664311DSC_0661.JPG",
                "https://cars-360.in/uploads/gallery/r_1539664311DSC_0658.JPG",
                "https://cars-360.in/uploads/gallery/1539664311DSC_0659.JPG",
                "https://cars-360.in/uploads/gallery/r_15396643121DSC_0664.JPG"
            ),
            Galleries(
                "https://cars-360.in/uploads/gallery/1539664311DSC_0661.JPG",
                "https://cars-360.in/uploads/gallery/r_1539664311DSC_0658.JPG",
                "https://cars-360.in/uploads/gallery/1539664311DSC_0659.JPG",
                "https://cars-360.in/uploads/gallery/r_15396643121DSC_0664.JPG"
            ),
            Galleries(
                "https://cars-360.in/uploads/gallery/1539664311DSC_0661.JPG",
                "https://cars-360.in/uploads/gallery/r_1539664311DSC_0658.JPG",
                "https://cars-360.in/uploads/gallery/1539664311DSC_0659.JPG",
                "https://cars-360.in/uploads/gallery/r_15396643121DSC_0664.JPG"
            )
        )
    }

}