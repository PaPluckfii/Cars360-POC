package com.sumeet.cars360.data.remote

import com.sumeet.cars360.data.remote.model.car_entities.CarEntitiesResponse
import com.sumeet.cars360.data.remote.model.car_entities.insert.BrandInsertOperation
import com.sumeet.cars360.data.remote.model.car_entities.insert.ModelInsertOperation
import com.sumeet.cars360.data.remote.model.service_logs.ServiceLogsByUserIdResponse
import com.sumeet.cars360.data.remote.model.service_logs.insert.ServiceLogInsertOperation
import com.sumeet.cars360.data.remote.model.user.UsersByFirebaseIdResponse
import com.sumeet.cars360.data.remote.model.user.insert.UserInsertOperation
import com.sumeet.cars360.data.remote.model.user_cars.CarDetailsResponseByUserId
import com.sumeet.cars360.data.remote.model.user_cars.insert.CarDetailsInsertOperation
import com.sumeet.cars360.data.repository.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {

    //User Data
    @POST("UserRegistration.php")
    suspend fun addNewUserToServer(
        @Body requestBody: RequestBody
    ): Response<UserInsertOperation>

    @POST("GetUserByFirebase.php")
    suspend fun getUserByUserId(
        @Body generalRequest: LoginByFirebaseRequest
    ): Response<UsersByFirebaseIdResponse>

    @POST("GetUserCarDetailByMobile.php")
    suspend fun getUserCarDetailsByMobileNumber(
        @Body carDetailsByMobileNumberRequest: CarDetailsByMobileNumberRequest
    ): Response<CarDetailsResponseByUserId>

    @POST("GetUserByUserType.php")
    suspend fun getUserByUserTypeId(
        @Body UserTypeId: String
    ): Response<UsersByFirebaseIdResponse>

    //User Car Details
    @POST("CarDetailSave.php")
    suspend fun addNewCar(
        @Body requestBody: RequestBody
    ): Response<CarDetailsInsertOperation>

    @POST("CarDetailUpdate.php")
    suspend fun updateExistingCar(
        @Body requestBody: RequestBody
    ): Response<CarDetailsInsertOperation>

    //TODO
    @POST("CarDetailList.php")
    suspend fun getCarDetailsListByUserId(
        @Body generalRequest: GeneralRequest
    ): Response<CarDetailsResponseByUserId>

    @POST("CarDetailUpdate.php")
    suspend fun deleteCarDetails(
        @Body carDetailsRequest: CarDetailsRequest
    ): Response<CarDetailsInsertOperation>

    //Service_Logs
    @POST("CarServiceAdd.php")
    suspend fun addNewServiceLog(
        @Body requestBody: RequestBody
    ): Response<ServiceLogInsertOperation>

    @POST("CarServiceUpdate.php")
    suspend fun updateServiceLog(
        @Body requestBody: RequestBody
    ): Response<ServiceLogInsertOperation>

    @POST("CarServiceList.php")
    suspend fun getAllServiceLogs(
        @Body generalRequest: GeneralRequest
    ): Response<ServiceLogsByUserIdResponse>

    @POST("CarServiceList.php")
    suspend fun updateServiceStatusById(
        @Body serviceStatusChangeRequest: ServiceStatusChangeRequest
    ): Response<ServiceLogInsertOperation>

    //Car Entities
    @POST("BrandSave.php")
    suspend fun addNewCarBrand(
        @Body requestBody: RequestBody
    ): Response<BrandInsertOperation>

    @POST("BrandUpdate.php")
    suspend fun updateCarBrand(
        @Body requestBody: RequestBody
    ): Response<BrandInsertOperation>

    @POST("ModelSave.php")
    suspend fun addNewCarModel(
        @Body requestBody: RequestBody
    ): Response<ModelInsertOperation>

    @POST("ModelUpdate.php")
    suspend fun updateCarModel(
        @Body requestBody: RequestBody
    ): Response<ModelInsertOperation>


    @POST("GetBrandModel.php")
    suspend fun getAllCarEntities(
        @Body generalRequest: GeneralRequest
    ): Response<CarEntitiesResponse>

    //TODO individual model and brand list and delete
//    @POST("ModelList.php")
//    suspend fun getAllCarModels(
//        @Body UserId: String,
//        @Body UserTypeId: String
//    ): Response<CarEntitiesResponse>

}