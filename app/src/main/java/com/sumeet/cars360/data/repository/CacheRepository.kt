package com.sumeet.cars360.data.repository

import androidx.room.withTransaction
import com.sumeet.cars360.data.local.Cars360RoomDatabase
import com.sumeet.cars360.data.local.room.model.*
import com.sumeet.cars360.data.remote.ApiClient
import com.sumeet.cars360.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

private const val DEFAULT_REQUEST_USER_ID = "11"
private const val DEFAULT_REQUEST_USER_TYPE_ID = "2"

class CacheRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val database: Cars360RoomDatabase
) {

    private val brandDao = database.carBrandDao()
    private val modelDao = database.carModelDao()
    private val customerDao = database.customerDao()
    private val serviceAdvisorDao = database.serviceAdvisorDao()
    private val carsDao = database.carDao()
    private val serviceLogDao = database.serviceLogDao()

    /**
     * Car Company
     */
    fun getAllCarBrands() = networkBoundResource(
        query = {
            brandDao.getAllCarBrands()
        },
        fetch = {
            delay(1000)
            apiClient.getAllCarEntities(
                GeneralRequest(
                    DEFAULT_REQUEST_USER_ID,
                    DEFAULT_REQUEST_USER_TYPE_ID
                )
            )
        },
        saveFetchResult = { carEntitiesResponse ->
            val carBrandEntities = mutableListOf<CarBrandEntity>()
            val carModelEntities = mutableListOf<CarModelEntity>()

            if (carEntitiesResponse.isSuccessful) {
                for (brand in carEntitiesResponse.body()?.carBrandResponse!!) {
                    carBrandEntities.add(
                        CarBrandEntity(
                            brandId = brand.brandId.toString(),
                            brandName = brand.brandName,
                            brandLogo = brand.brandLogo
                        )
                    )
                    for (model in brand.carModelResponses!!) {
                        carModelEntities.add(
                            CarModelEntity(
                                modelId = model.modelId.toString(),
                                modelName = model.modelName,
                                modelLogo = model.modelImage,
                                brandId = brand.brandId
                            )
                        )
                    }
                }
                database.withTransaction {
                    brandDao.apply {
                        deleteAllCarBrands()
                        insertAllCarBrands(carBrandEntities)
                    }
                    modelDao.apply {
                        deleteAllCarModels()
                        insertAllCarModels(carModelEntities)
                    }
                }
            }
        }
    )

    fun getCarModelsByBrands(brandId: String) = modelDao.getAllCarModelsForBrand(brandId)


    /**
     * Users
     */
    fun getAllCustomers() = networkBoundResource(
        query = {
            customerDao.getAllCustomers()
        },
        fetch = {
            delay(1000)
            apiClient.getUserByUserTypeId("3")
        },
        saveFetchResult = { userByFirebaseIdResponse ->
            val customerEntities = mutableListOf<CustomerEntity>()

            if (userByFirebaseIdResponse.isSuccessful) {
                for (user in userByFirebaseIdResponse.body()?.userResponse!!) {
                    user.toUserEntity()?.let { customerEntities.add(it) }
                }
                database.withTransaction {
                    customerDao.apply {
                        deleteAllCustomers()
                        insertAllCustomers(customerEntities)
                    }
                }
            }
        }
    )

    fun getAllServiceAdvisors() = networkBoundResource(
        query = {
            serviceAdvisorDao.getAllServiceAdvisors()
        },
        fetch = {
            delay(1000)
            apiClient.getUserByUserTypeId("2")
        },
        saveFetchResult = { userByFirebaseIdResponse ->
            val serviceAdvisorEntities = mutableListOf<ServiceAdvisorEntity>()

            if (userByFirebaseIdResponse.isSuccessful) {
                for (user in userByFirebaseIdResponse.body()?.userResponse!!) {
                    user.toServiceAdvisorEntity()?.let { serviceAdvisorEntities.add(it) }
                }
                database.withTransaction {
                    serviceAdvisorDao.apply {
                        deleteALlServiceAdvisors()
                        insertAllServiceAdvisors(serviceAdvisorEntities)
                    }
                }
            }
        }
    )

    /**
     * Customer Cars
     */
    fun getAllCustomerCars() = carsDao.getAllCars()

    fun getCarsByCustomerId(userId: String) = carsDao.getCarsByCustomerId(userId)

    /**
     * Service Logs
     */
    fun getAllServiceLogs() = networkBoundResource(
        query = {
            serviceLogDao.getAllServiceLogs()
        },
        fetch = {
            apiClient.getAllServiceLogs(
                GeneralRequest(
                    DEFAULT_REQUEST_USER_ID,
                    DEFAULT_REQUEST_USER_TYPE_ID
                )
            )
        },
        saveFetchResult = { serviceLogsResponse ->
            val serviceLogEntities = mutableListOf<ServiceLogEntity>()

            if (serviceLogsResponse.isSuccessful) {
                for (serviceLog in serviceLogsResponse.body()?.serviceLogResponse!!) {
                    serviceLog.toServiceLogEntity()?.let { serviceLogEntities.add(it) }
                }
                database.withTransaction {
                    serviceLogDao.apply {
                        deleteAllServiceLogs()
                        insertAllLogs(serviceLogEntities)
                    }
                }
            }
        }
    )

    fun getAllServiceLogsBasedOnStatus(status: String) =
        serviceLogDao.getALlServiceLogsBasedOnStatus(status)

}