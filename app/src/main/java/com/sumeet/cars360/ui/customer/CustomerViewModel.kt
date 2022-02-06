package com.sumeet.cars360.ui.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeet.cars360.data.local.room.model.CarEntity
import com.sumeet.cars360.data.local.room.model.UserEntity
import com.sumeet.cars360.data.remote.old_model.Galleries
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val remoteRepository: RemoteRepository
): ViewModel() {

    init {
        getMyUser()
        getMyCars()
    }

    private val _myUser = MutableLiveData<UserEntity>()
    val myUser: LiveData<UserEntity>
    get() = _myUser

    private val _myCars = MutableLiveData<List<CarEntity>>()
    val myCars: LiveData<List<CarEntity>>
    get() = _myCars

    private fun getMyUser(){
        viewModelScope.launch {
            roomRepository.getMyUser().collect {
                _myUser.postValue(it)
            }
        }
    }

    private fun getMyCars() {
        viewModelScope.launch {
            roomRepository.getMyCars().collect {
                if(it.isNotEmpty())
                    _myCars.postValue(it)
                else
                    _myCars.postValue(emptyList())
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