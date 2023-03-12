package com.example.healthcare.APICalls

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.healthcare.Activity.LogInActivity
import com.example.healthcare.Activity.SignUpActivity
import com.example.healthcare.Home
import com.example.healthcare.models.Doctor
import com.example.healthcare.models.Result
import com.example.healthcare.models.User
import com.example.healthcare.network.ApiService
import com.example.healthcare.utils.BaseActivity

import com.example.healthcare.utils.Constants
import retrofit.*

class API {
    fun registerUser(user:User,activity: SignUpActivity){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService =retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<Result> = service.createuser(user)
        call.enqueue(object : Callback<Result> {
            override fun onResponse(response: Response<Result>?, retrofit: Retrofit?) {
                //print(response?.body().toString())
                //activity.sigin()
                if(response!!.isSuccess){
                    val result:com.example.healthcare.models.Result=response.body()
                    if(result.res.equals("Register")){

                        activity.sigin()

                    }else{
                       Toast.makeText(activity,result.res,Toast.LENGTH_SHORT).show()
                    }

                }

            }

            override fun onFailure(t: Throwable?) {
                BaseActivity().hideProgressDialog()
                Toast.makeText(activity,"Something went Wrong",Toast.LENGTH_LONG).show()

            }

        })




    }
    //login
    fun Login(user:User,activity: LogInActivity){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService =retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<Result> = service.loginn(user)
        call.enqueue(object : Callback<Result> {
            override fun onResponse(response: Response<Result>?, retrofit: Retrofit?) {
                //print(response?.body().toString())
                //activity.sigin()
                if(response!!.isSuccess){
                    val result:com.example.healthcare.models.Result=response.body()
                    if(result.res.equals("Logged In")){

                        activity.loggedinsucess(result.res)

                    }else{
                        Toast.makeText(activity,result.res,Toast.LENGTH_SHORT).show()
                    }

                }

            }

            override fun onFailure(t: Throwable?) {
               // BaseActivity().hideProgressDialog()
                Toast.makeText(activity,"Something went Wrong",Toast.LENGTH_LONG).show()

            }

        })
    }
    //gettting dotor list
    fun getdoctorslist(context:Context, callback: (ArrayList<Doctor>) -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<List<Doctor>> = service.getdoctorlist()

        call.enqueue(object : Callback<List<Doctor>> {
            override fun onResponse(response: Response<List<Doctor>>?, retrofit: Retrofit?) {
                if(response!!.isSuccess){
                    val doctorlist: ArrayList<Doctor> = response.body() as ArrayList<Doctor>
                    callback(doctorlist)
                }
            }

            override fun onFailure(t: Throwable?) {
                // handle failure
            }
        })
    }

}