package com.example.healthcare.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.healthcare.APICalls.API
import com.example.healthcare.R
import com.example.healthcare.models.Doctor
import com.example.healthcare.models.User
import com.example.healthcare.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_booking.*

class BookingActivity : BaseActivity() {
    var currDoctor:Doctor?=null
    var email:String?=null
    private lateinit var mSharedPrefernces: SharedPreferences
    var curruser: User?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        setupActionBar()
        val intent = intent
        val doctor = intent.getSerializableExtra("doctor") as Doctor
        mSharedPrefernces = this.getSharedPreferences("user", Context.MODE_PRIVATE)
         email=mSharedPrefernces.getString("email","")




        getdoctorSlot(doctor.email)
        ll_slot1.setOnClickListener {
            API().getUserbyId(email!!){user->
                //curruser=user
                if(user.money>=150){
                    currDoctor!!.slot10_11=true
                    showAlertandbookslot(this,currDoctor!!," 10 AM - 11 AM")
                    user.money=user.money-150
                    API().updateMoney(user){
                        Toast.makeText(this,"Slot Booked ",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    showErrorSnackBar("You dont have enough Money.")
                }
            }


        }
        ll_slot2.setOnClickListener {
            API().getUserbyId(email!!){user->
                //curruser=user
                if(user.money>=150){
                    currDoctor!!.slot11_12=true
                    showAlertandbookslot(this,currDoctor!!," 11 AM - 12 PM")
                    user.money=user.money-150
                    API().updateMoney(user){
                        Toast.makeText(this,"Slot Booked ",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    showErrorSnackBar("You dont have enough Money.")
                }
            }


        }
        ll_slot3.setOnClickListener {
            API().getUserbyId(email!!){user->
                //curruser=user
                if(user.money>=150){
                    currDoctor!!.slot1_2=true
                    showAlertandbookslot(this,currDoctor!!," 1 PM - 2 PM")
                    user.money=user.money-150
                    API().updateMoney(user){
                        Toast.makeText(this,"Slot Booked ",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    showErrorSnackBar("You dont have enough Money.")
                }
            }
        }
        ll_slot4.setOnClickListener {
            //Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show()
            API().getUserbyId(email!!){user->
                //curruser=user
                if(user.money>=150){
                    currDoctor!!.slot2_3=true
                    //startActivity(Intent(this,AddressActivity::class.java))
                    showAlertandbookslot(this,currDoctor!!," 2 PM - 3 PM")
                    user.money=user.money-150
                    API().updateMoney(user){
                        Toast.makeText(this,"Slot Booked ",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    showErrorSnackBar("You dont have enough Money.")
                }
            }


        }


    }

    fun getdoctorSlot(email:String){
        showProgressDialog("Wait...")

        API().getDcotorbyId(email){doctor ->

            currDoctor=doctor
            if(doctor.slot10_11==false){
                tv_slot1.setText("SLOT 1 10.00 AM - 11.00 AM Click for Booking")
                //currDoctor!!.slot10_11=false
                //showAlert(this,currDoctor!!)
                //showalterdialogh(email,currDoctor!!)
            }else{
                tv_slot1.setText("Booked")
                ll_slot1.isClickable = false
                ll_slot1.isFocusable = false
            }
            if(doctor.slot11_12==false){
                tv_slot2.setText("SLOT 1 11.00 AM - 12.00 AM Click for Booking")
                //currDoctor!!.slot11_12=false
                //showAlert(this,currDoctor!!)
                //showalterdialogh(email,currDoctor!!)
            }else{
                tv_slot2.setText("Booked")
                ll_slot2.isClickable = false
                ll_slot2.isFocusable = false
            }
            if(doctor.slot1_2==false){
                tv_slot3.setText("SLOT 1 1.00 PM - 2.00 PM Click for Booking")
                //currDoctor!!.slot10_11=false
                //showAlert(this,currDoctor!!)
                //showalterdialogh(email,currDoctor!!)
            }else{
                tv_slot3.setText("Booked")
                ll_slot3.isClickable = false
                ll_slot3.isFocusable = false
            }
            if(doctor.slot2_3==false){
                tv_slot4.setText("SLOT 1 2.00 PM - 3.00 PM Click for Booking")
                //currDoctor!!.slot10_11=false
                //showAlert(this,currDoctor!!)
                //showalterdialogh(email,currDoctor!!)
            }else{
                tv_slot4.setText("Booked")
                ll_slot4.isClickable = false
                ll_slot4.isFocusable = false
            }
            hideProgressDialog()

        }
    }
    private fun setupActionBar() {

        setSupportActionBar(clicn_booking_toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)
            actionBar.title = "Booking"
        }

        clicn_booking_toolbar.setNavigationOnClickListener { onBackPressed() }
    }
    fun showAlertandbookslot(context: Context,doctor: Doctor,slot:String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialogh, null)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("SlotBooking")
            .setCancelable(false)
            .setPositiveButton("OK and Pay") { dialog, which ->
                // do something when OK button is clicked
                if(currDoctor!!.ishomvisit==true){
                    API().updateDoctorSlot(doctor,this,slot,email!!){


                    }
                    val intent=Intent(this,AddressActivity::class.java)
                    intent.putExtra("doctoremail", currDoctor!!.email)
                    intent.putExtra("slot",slot)
                    startActivity(intent)


                }else{
                    API().updateDoctorSlot(doctor,this,slot,email!!){

                    }

                }



                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // do something when Cancel button is clicked
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }






}