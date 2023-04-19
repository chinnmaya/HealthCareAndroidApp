package com.example.healthcare.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.healthcare.APICalls.API
import com.example.healthcare.R
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_booking.*

class AddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        setupActionBar()
        val docemail = intent.getStringExtra("doctoremail")
        val slot=intent.getStringExtra("slot")


        btn_submit.setOnClickListener {
            if (docemail != null && slot!=null) {
                getData(docemail,slot)
            }
            finish()
            startActivity(Intent(this,HomeActivity::class.java))
            Toast.makeText(this,"Slot Booked, Check your Mail",Toast.LENGTH_SHORT).show()

        }
    }

    fun getData(docemail:String,slot:String){
        val name:String=et_name.text.toString().trim()
        val email:String=et_email.text.toString().trim()
        val phone:String=et_phone.text.toString().trim()
        val street:String=et_street.text.toString().trim()
        val pincode:String=et_pin.text.toString().trim()
        val dist:String=et_dist.text.toString().trim()
        val state:String=et_state.text.toString().trim()
        val patientDetails:String= name+" , Email : "+email+" , Phone : "+phone+" , street : "+street+" , Pincode : "+pincode+" , Dist : "+dist+" , State"+state
        API().sendEmailHomeVisit(docemail,slot,patientDetails,email){

        }
        //Toast.makeText(this,patientDetails,Toast.LENGTH_SHORT).show()


    }
    private fun setupActionBar() {
        setSupportActionBar(adress_toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)
            actionBar.title = "Adress"
        }
        adress_toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}