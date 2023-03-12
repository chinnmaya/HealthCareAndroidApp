package com.example.healthcare.network

import com.example.healthcare.models.Doctor
import com.example.healthcare.models.Result
import com.example.healthcare.models.User
import retrofit.Call
import retrofit.http.Body
import retrofit.http.GET
import retrofit.http.POST

interface ApiService {
    @POST("/addUser")
    fun createuser(@Body user: User):Call<Result>
    @POST("/signin")
    fun loginn(@Body user: User):Call<Result>
    @GET("/getdoctorlist")
    fun getdoctorlist():Call<List<Doctor>>
}