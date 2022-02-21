package com.sumeet.cars360.ui.customer

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.data.remote.model.user.UserResponse
import com.sumeet.cars360.data.remote.model.user.UsersByFirebaseIdResponse
import com.sumeet.cars360.data.remote.model.user_cars.CarDetailsResponseByUserId
import com.sumeet.cars360.data.remote.old_model.Galleries
import com.sumeet.cars360.data.repository.RemoteRepository
import com.sumeet.cars360.util.Constants.NO_INTERNET_CONNECTION
import com.sumeet.cars360.util.Constants.hasInternetConnection
import com.sumeet.cars360.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
): ViewModel() {

    private val _customerDetails = MutableLiveData<Resource<UsersByFirebaseIdResponse>>()
    val customerDetails:LiveData<Resource<UsersByFirebaseIdResponse>> = _customerDetails

    private val _customerCarDetailsData = MutableLiveData<Resource<CarDetailsResponseByUserId>>()
    val customerCarDetailsData: LiveData<Resource<CarDetailsResponseByUserId>> = _customerCarDetailsData

    fun setUserData(context: Context,userResponse: UserResponse) {
        SavePrefs(context).apply {
            saveUserName(userResponse.name.toString())
            saveUserEmail(userResponse.email.toString())
            saveUserAddress(userResponse.address.toString())
            saveUserCity(userResponse.city.toString())
            saveUserCountry(userResponse.country.toString())
            saveUserState(userResponse.state.toString())
            saveUserPostalCode(userResponse.postalCode.toString())
            saveUserDob(userResponse.dOB.toString())
            saveUserGstin(userResponse.gSTIN.toString())
            saveProfileImage(userResponse.profileImage.toString())
            saveUserDom(userResponse.dOM.toString())
        }
    }


    fun getCustomerByUserId(userId:String){
        _customerDetails.postValue(Resource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getCustomerByFirebaseId(userId)
                if (response.isSuccessful && response.body() != null)
                    if (response.body()?.error == false)
                        _customerDetails.postValue(Resource.Success(response.body()))
                    else
                        _customerDetails.postValue(Resource.Error(response.body()?.message))
                else
                    _customerDetails.postValue(Resource.Error(response.message()))
            }else{
                _customerDetails.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }

    fun getCustomerCarDetailsByMobileNumber(mobileNo: String) {
        _customerCarDetailsData.postValue(Resource.Loading())
        viewModelScope.launch {
            if (hasInternetConnection()){
                val response = remoteRepository.getCustomerCarDetailsByMobileNumber(mobileNo)
                if (response.isSuccessful && response.body() != null)
                    if (response.body()?.error == false)
                        _customerCarDetailsData.postValue(Resource.Success(response.body()))
                    else
                        _customerCarDetailsData.postValue(Resource.Error(response.body()?.message))
                else
                    _customerCarDetailsData.postValue(Resource.Error(response.message()))
            }else{
                _customerCarDetailsData.postValue(Resource.Error(NO_INTERNET_CONNECTION))
            }
        }
    }


 /*   init {
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
    }*/

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