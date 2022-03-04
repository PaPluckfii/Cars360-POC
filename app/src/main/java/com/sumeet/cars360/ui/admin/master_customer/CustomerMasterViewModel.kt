package com.sumeet.cars360.ui.admin.master_customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sumeet.cars360.data.repository.CacheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomerMasterViewModel @Inject constructor(
    private val cacheRepository: CacheRepository
): ViewModel(){

    val customers = cacheRepository.getAllCustomers().asLiveData()

}