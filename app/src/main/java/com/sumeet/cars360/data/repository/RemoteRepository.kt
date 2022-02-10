package com.sumeet.cars360.data.repository

import com.sumeet.cars360.data.ServiceLogStatus
import com.sumeet.cars360.data.remote.ApiClient
import com.sumeet.cars360.data.remote.model.car_entities.insert.BrandInsertOperation
import com.sumeet.cars360.data.remote.model.car_entities.insert.ModelInsertOperation
import com.sumeet.cars360.data.remote.model.service_logs.insert.ServiceLogInsertOperation
import com.sumeet.cars360.data.remote.model.user.insert.UserInsertOperation
import com.sumeet.cars360.data.remote.model.user_cars.insert.CarDetailsInsertOperation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiClient: ApiClient
) {

    //USER DATA

    suspend fun addNewUserToServer(
        name: String,
        email: String,
        mobileNo: String,
        firebaseId: String,
        address: String,
        city: String,
        state: String,
        pinCode: String,
        dob: String,
        dom: String,
        gstIn: String,
        profileImage: File?
    ): Response<UserInsertOperation> {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("UserTypeId", "3")
            .addFormDataPart("Name", name)
            .addFormDataPart("Email", email)
            .addFormDataPart("Mobile", mobileNo)
            .addFormDataPart("FirebaseId", firebaseId)
            .addFormDataPart("Address", address)
            .addFormDataPart("City", city)
            .addFormDataPart("State", state)
            .addFormDataPart("PostalCode", pinCode)
            .addFormDataPart("DOB", dob)
            .addFormDataPart("DOM", dom)
            .addFormDataPart("CreatedBy", "1")
            .addFormDataPart("Pwd", "")
            .addFormDataPart("GSTIN", gstIn)

        if (profileImage != null)
            requestBody.addFormDataPart(
                name = "ProfileImage",
                filename = profileImage.name,
                body = profileImage.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )

        return apiClient.addNewUserToServer(requestBody.build())
    }

    suspend fun getUserByUserId(userId: String) =
        apiClient.getUserByUserId(GeneralRequest(userId,""))

    suspend fun getCarDetailsByMobileNumber(mobileNo: String) =
        apiClient.getUserCarDetailsByMobileNumber(CarDetailsByMobileNumberRequest("1",mobileNo))

    suspend fun getUserByUserTypeId(userTypeId: String) =
        apiClient.getUserByUserTypeId(userTypeId)


    //CAR DETAILS

    suspend fun addNewCarDetails(
        userId: String,
        modelId: String,
        brandId: String,
        vehicleNo: String,
        bodyColor: String,
        plateNo: String,
        fuelType: String,
        insuranceCompany: String,
        insuranceExpiryDate: String,
        createdBy: String
    ): Response<CarDetailsInsertOperation> {

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("UserId", userId)
            .addFormDataPart("ModelId", modelId)
            .addFormDataPart("BrandId", brandId)
            .addFormDataPart("VehicleNo", vehicleNo)
            .addFormDataPart("Color", bodyColor)
            .addFormDataPart("PlateNo", plateNo)
            .addFormDataPart("FuelType", fuelType)
            .addFormDataPart("InsuranceCompany", insuranceCompany)
            .addFormDataPart("ExpiryDate", insuranceExpiryDate)
            .addFormDataPart("CreatedBy", createdBy)
            .build()

        return apiClient.addNewCar(requestBody)
    }

    suspend fun updateExistingCar(
        carId: String,
        userId: String,
        modelId: String,
        brandId: String,
        vehicleNo: String,
        bodyColor: String,
        plateNo: String,
        fuelType: String,
        insuranceCompany: String,
        insuranceExpiryDate: String,
        modifiedBy: String
    ): Response<CarDetailsInsertOperation> {

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("CarId", carId)
            .addFormDataPart("UserId", userId)
            .addFormDataPart("ModelId", modelId)
            .addFormDataPart("BrandId", brandId)
            .addFormDataPart("VehicleNo", vehicleNo)
            .addFormDataPart("Color", bodyColor)
            .addFormDataPart("PlateNo", plateNo)
            .addFormDataPart("FuelType", fuelType)
            .addFormDataPart("InsuranceCompany", insuranceCompany)
            .addFormDataPart("ExpiryDate", insuranceExpiryDate)
            .addFormDataPart("ModifiedBy", modifiedBy)
            .build()

        return apiClient.updateExistingCar(requestBody)
    }

    suspend fun getCarDetailsListByUserId(
        userId: String,
        userTypeId: String
    ) = apiClient.getCarDetailsListByUserId(GeneralRequest(userId, userTypeId))

    suspend fun deleteCarDetails(
        userId: String,
        userTypeId: String,
        carId: String
    ) = apiClient.deleteCarDetails(CarDetailsRequest(userId, userTypeId, carId))


    //SERVICE_LOGS

    suspend fun addNewServiceLog(
        carId: String,
        accessories: String,
        serviceTypes: String,
        estimates: String,
        additionalDetails: String,
        userCarRequest: String,
        originalAmount: String,
        estimatedAmount: String,
        paidAmount: String,
        paymentMode: String,
        createdBy: String,
        leftPic: File?,
        rightPic: File?,
        frontPic: File?,
        backPic: File?
    ): Response<ServiceLogInsertOperation> {

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("CarId", carId)
            .addFormDataPart("Accessories", accessories)
            .addFormDataPart("ServiceTypes", serviceTypes)
            .addFormDataPart("Estimates", estimates)
            .addFormDataPart("AdditionalDetail", additionalDetails)
            .addFormDataPart("UserCarRequests", userCarRequest)
            .addFormDataPart("OriginalAmount", originalAmount)
            .addFormDataPart("EstimatedAmount", estimatedAmount)
            .addFormDataPart("PaidAmount", paidAmount)
            .addFormDataPart("PaymentMode", paymentMode)
            .addFormDataPart("CreatedBy", createdBy)

        if (leftPic != null)
            requestBody.addFormDataPart(
                name = "LeftPic",
                filename = leftPic.name,
                body = leftPic.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )

        if (rightPic != null)
            requestBody.addFormDataPart(
                name = "RightPic",
                filename = rightPic.name,
                body = rightPic.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )

        if (frontPic != null)
            requestBody.addFormDataPart(
                name = "FrontPic",
                filename = frontPic.name,
                body = frontPic.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )

        if (backPic != null)
            requestBody.addFormDataPart(
                name = "BackPic",
                filename = backPic.name,
                body = backPic.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )

        return apiClient.addNewServiceLog(requestBody.build())
    }

    suspend fun updateExistingServiceLog(
        serviceLogId: String,
        carId: String,
        accessories: String,
        serviceTypes: String,
        estimates: String,
        additionalDetails: String,
        userCarRequest: String,
        originalAmount: String,
        estimatedAmount: String,
        paidAmount: String,
        paymentMode: String,
        createdBy: String,
        leftPic: File,
        rightPic: File,
        frontPic: File,
        backPic: File
    ): Response<ServiceLogInsertOperation> {

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("CarServiceId", serviceLogId)
            .addFormDataPart("CarId", carId)
            .addFormDataPart("Accessories", accessories)
            .addFormDataPart("ServiceTypes", serviceTypes)
            .addFormDataPart("Estimates", estimates)
            .addFormDataPart("AdditionalDetail", additionalDetails)
            .addFormDataPart("UserCarRequests", userCarRequest)
            .addFormDataPart("OriginalAmount", originalAmount)
            .addFormDataPart("EstimatedAmount", estimatedAmount)
            .addFormDataPart("PaidAmount", paidAmount)
            .addFormDataPart("PaymentMode", paymentMode)
            .addFormDataPart("ModifiedBy", createdBy)
            .addFormDataPart("CarImageId", "2")
            .addFormDataPart(
                name = "LeftPic",
                filename = leftPic.name,
                body = leftPic.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
            .addFormDataPart(
                name = "RightPic",
                filename = rightPic.name,
                body = rightPic.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
            .addFormDataPart(
                name = "FrontPic",
                filename = frontPic.name,
                body = frontPic.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
            .addFormDataPart(
                name = "BackPic",
                filename = backPic.name,
                body = backPic.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
            .build()
        return apiClient.updateServiceLog(requestBody)
    }

    suspend fun getAllServiceLogsByUserId(userId: String) =
        apiClient.getAllServiceLogs(GeneralRequest(userId, "2"))

    suspend fun updateServiceLogStatus(
        serviceLogId: String,
        status: ServiceLogStatus,
        modifiedBy: String
    ): Response<ServiceLogInsertOperation> {

        val uploadStatus = when (status) {
            is ServiceLogStatus.PENDING -> 1
            is ServiceLogStatus.READY -> 2
            is ServiceLogStatus.DELIVERED -> 3
            is ServiceLogStatus.CANCELLED -> 4
            is ServiceLogStatus.RE_ESTIMATE -> 5
            else -> 1
        }.toString()

        return apiClient.updateServiceStatusById(
            ServiceStatusChangeRequest(
                serviceLogId,
                uploadStatus,
                modifiedBy
            )
        )
    }


    //CAR_ENTITIES

    suspend fun addNewCarBrand(
        brandName: String,
        brandLogo: File
    ): Response<BrandInsertOperation> {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("BrandName", brandName)
            .addFormDataPart(
                name = "BrandLogo",
                filename = brandLogo.name,
                body = brandLogo.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
            .build()

        return apiClient.addNewCarBrand(requestBody)
    }

    suspend fun addNewCarModel(
        brandId: String,
        modelName: String,
        modelLogo: File
    ): Response<ModelInsertOperation> {

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("BrandName", modelName)
            .addFormDataPart(
                name = "BrandLogo",
                filename = modelLogo.name,
                body = modelLogo.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
            .build()
        return apiClient.addNewCarModel(requestBody)
    }

    suspend fun updateExistingBrand(
        brandId: String,
        brandName: String,
        brandLogo: File
    ): Response<BrandInsertOperation> {

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("BrandId", brandId)
            .addFormDataPart("BrandName", brandName)
            .addFormDataPart(
                name = "BrandLogo",
                filename = brandLogo.name,
                body = brandLogo.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
            .build()

        return apiClient.updateCarBrand(requestBody)
    }

    suspend fun updateExistingModel(
        modelId: String,
        brandId: String,
        modelName: String,
        modelLogo: File
    ): Response<ModelInsertOperation> {

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("ModelId", modelId)
            .addFormDataPart("BrandId", brandId)
            .addFormDataPart("ModelName", modelName)
            .addFormDataPart(
                name = "ModeImage",
                filename = modelLogo.name,
                body = modelLogo.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
            .build()

        return apiClient.updateCarModel(requestBody)
    }

    suspend fun getAllCarCollections() =
        apiClient.getAllCarEntities(GeneralRequest("11", "2"))

}

data class CarDetailsByMobileNumberRequest(
    val UserTypeId: String,     //User Type Id for Customer = 3
    val Mobile: String
)

data class GeneralRequest(
    val UserId: String,
    val UserTypeId: String
)

data class CarDetailsRequest(
    val UserId: String,
    val UserTypeId: String,
    val CarId: String
)

data class ServiceStatusChangeRequest(
    val CarServiceId: String,
    val StatusId: String,
    val ModifiedBy: String
)