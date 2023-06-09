package com.example.healthcare.network

import android.provider.ContactsContract.CommonDataKinds.StructuredName
import com.example.healthcare.models.Doctor
import com.example.healthcare.models.Result
import com.example.healthcare.models.User
import retrofit.Call
import retrofit.http.Body
import retrofit.http.GET
import retrofit.http.POST
import retrofit.http.PUT
import retrofit.http.Query

interface ApiService {
    @POST("/addUser")
    fun createuser(@Body user: User):Call<Result>
    @POST("/signin")
    fun loginn(@Body user: User):Call<Result>
    @GET("/getdoctorlistgetdoctorlistbyclicnvisit")
    fun getdoctorlistbyclicncvisit():Call<List<Doctor>>
    @GET("/getdoctorlist")
    fun getdoctorlist():Call<List<Doctor>>
    @GET("/getdoctorbycategory")
    fun getdoctorlistbycategory(
        @Query("q") category:String,

    ):Call<List<Doctor>>
    @GET("/getUserbyId")
    fun getUserbyId(
        @Query("q") email:String,
    ):Call<User>
    @PUT("/updateMoney")
    fun addMoney(@Body user: User):Call<String>
    @GET("/getMoney")
    fun getMoney(
        @Query("q") email:String
    ):Call<Int>
    @GET("/getDoctorbyId")
    fun getDoctorbyId(
        @Query("q") email:String,
    ):Call<Doctor>
    @POST("/updatedoctorslot")
    fun updateDoctorSlot(@Body doctor: Doctor):Call<String>
    @GET("/sendEmail")
    fun sendEmail(
        @Query("email") email:String,
        @Query("slot") slot:String,
    ):Call<String>
    @GET("/getDoctorsByHomeVisit")
    fun getDoctorsByHomeVisit():Call<List<Doctor>>
    @GET("/SendMailHomeVisit")
    fun sendmailHomeVisit(@Query("emaildoc") emaildoc:String,
                          @Query("emailuser") emailuser:String,
                          @Query("slot") slot:String,
                          @Query("address") address:String
    ):Call<String>
}