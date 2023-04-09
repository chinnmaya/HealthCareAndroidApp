package com.example.healthcare.APICalls

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.healthcare.Activity.ClicnVisitBookingActivity
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

class API :BaseActivity(){
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

                        activity.loggedinsucess(user.email)

                    }else{
                        Toast.makeText(activity,result.res,Toast.LENGTH_SHORT).show()
                    }

                }

            }

            override fun onFailure(t: Throwable?) {
                hideProgressDialog()
               // BaseActivity().hideProgressDialog()
                Toast.makeText(activity,"Something went Wrong",Toast.LENGTH_LONG).show()

            }

        })
    }
    //gettting dotor list
    fun getdoctorslistbyclicnvisit(context:Context, callback: (ArrayList<Doctor>) -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<List<Doctor>> = service.getdoctorlistbyclicncvisit()

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
    fun getdocotorlistbycategory(context: Context,category:String,callback: (ArrayList<Doctor>) -> Unit){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<List<Doctor>> = service.getdoctorlistbycategory(category)

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
    fun getUserbyId(email:String,callback: (User) -> Unit){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<User> = service.getUserbyId(email)

        call.enqueue(object : Callback<User> {
            override fun onResponse(response: Response<User>?, retrofit: Retrofit?) {
                if(response!!.isSuccess){
                    val user:User? = response.body()

                    if(user!=null){
                        callback(user)

                    }

                }
            }

            override fun onFailure(t: Throwable?) {
                // handle failure
            }
        })

    }
    fun updateMoney(user:User,callback: (Void) -> Unit){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService =retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<String> = service.addMoney(user)
        call.enqueue(object : Callback<String> {
            override fun onResponse(response: Response<String>?, retrofit: Retrofit?) {
                //print(response?.body().toString())
                //activity.sigin()
                if(response!!.isSuccess){
                    val result=response.body()
                    if(result.equals("sucesss")){
                        callback

                        // activity.loggedinsucess(user.email)

                    }else{
                        // Toast.makeText(activity,result.res,Toast.LENGTH_SHORT).show()
                    }

                }

            }

            override fun onFailure(t: Throwable?) {
                //hideProgressDialog()
                // BaseActivity().hideProgressDialog()
                //Toast.makeText(activity,"Something went Wrong",Toast.LENGTH_LONG).show()

            }

        })
    }
    fun getMoney(email:String,callback: (Int) -> Unit){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<Int> = service.getMoney(email)

        call.enqueue(object : Callback<Int> {
            override fun onResponse(response: Response<Int>?, retrofit: Retrofit?) {
                if(response!!.isSuccess){
                    val money = response.body()

                    if(money!=null){
                        callback(money)

                    }

                }
            }

            override fun onFailure(t: Throwable?) {
                // handle failure
            }
        })

    }
    fun getDcotorbyId(email:String,callback: (Doctor) -> Unit){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<Doctor> = service.getDoctorbyId(email)

        call.enqueue(object : Callback<Doctor> {
            override fun onResponse(response: Response<Doctor>?, retrofit: Retrofit?) {
                if(response!!.isSuccess){
                    val doctor:Doctor? = response.body()

                    if(doctor!=null){
                        callback(doctor)

                    }

                }
            }

            override fun onFailure(t: Throwable?) {
                // handle failure
            }
        })

    }
    fun updateDoctorSlot(doctor: Doctor,Activity:ClicnVisitBookingActivity,slot:String,email:String,callback: (Void) -> Unit){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService =retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<String> = service.updateDoctorSlot(doctor)
        call.enqueue(object : Callback<String> {
            override fun onResponse(response: Response<String>?, retrofit: Retrofit?) {
                //print(response?.body().toString())
                //activity.sigin()
                if(response!!.isSuccess){
                    val result=response.body()
                    if(result.equals("sucesss")){
                        sendEmail(email,slot){}
                        Activity.getdoctorSlot(doctor.email)
                        callback


                        // activity.loggedinsucess(user.email)

                    }else{
                         Toast.makeText(Activity,"fail1",Toast.LENGTH_SHORT).show()
                    }

                }

            }

            override fun onFailure(t: Throwable?) {
                Toast.makeText(Activity,t.toString(),Toast.LENGTH_SHORT).show()
                //hideProgressDialog()
                // BaseActivity().hideProgressDialog()
                //Toast.makeText(activity,"Something went Wrong",Toast.LENGTH_LONG).show()

            }

        })
    }

    fun sendEmail(email:String,slot:String,callback: (Void) -> Unit){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService =retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<String> = service.sendEmail(email,slot)
        call.enqueue(object : Callback<String> {
            override fun onResponse(response: Response<String>?, retrofit: Retrofit?) {
                //print(response?.body().toString())
                //activity.sigin()
                if(response!!.isSuccess){
                    val result=response.body()
                    if(result.equals("sucesss")){
                        //Activity.getdoctorSlot(doctor.email)
                        callback


                        // activity.loggedinsucess(user.email)

                    }else{
                        //Toast.makeText(Activity,"fail1",Toast.LENGTH_SHORT).show()
                    }

                }

            }

            override fun onFailure(t: Throwable?) {
                //Toast.makeText(Activity,t.toString(),Toast.LENGTH_SHORT).show()
                //hideProgressDialog()
                // BaseActivity().hideProgressDialog()
                //Toast.makeText(activity,"Something went Wrong",Toast.LENGTH_LONG).show()

            }

        })
    }
    fun getdocotorlistbyhomeVist(callback: (ArrayList<Doctor>) -> Unit){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)
        val call: Call<List<Doctor>> = service.getDoctorsByHomeVisit()

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