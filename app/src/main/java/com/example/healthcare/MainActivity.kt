package com.example.healthcare


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.healthcare.Activity.HomeActivity
import com.example.healthcare.Activity.IntroActivity
import com.example.healthcare.utils.BaseActivity
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.fragment_doctorprofile.*
import kotlinx.coroutines.delay

class MainActivity : BaseActivity() {
    private lateinit var mSharedPrefernces: SharedPreferences

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showProgressDialog("please wait..")
        mSharedPrefernces=getSharedPreferences("user", Context.MODE_PRIVATE)
        val user=mSharedPrefernces.getString("email","")


        if(!user.isNullOrEmpty()){
           // val intent = Intent(this@MainActivity, HomeActivity::class.java)
            //intent.putExtra("email", user)
            //startActivity(intent)
           // Toast.makeText(this,user, Toast.LENGTH_LONG).show()
            val intent1= Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent1)
            finish()
        }else{


            startActivity(Intent(this,IntroActivity::class.java))
            finish()
        }
        hideProgressDialog()

    }
}