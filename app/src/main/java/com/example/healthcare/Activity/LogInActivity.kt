package com.example.healthcare.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.example.healthcare.APICalls.API
import com.example.healthcare.R
import com.example.healthcare.models.User
import com.example.healthcare.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : BaseActivity() {
    private lateinit var mSharedPrefernces: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        mSharedPrefernces=getSharedPreferences("user", Context.MODE_PRIVATE)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupActionBar()
        btn_sign_in.setOnClickListener {
            signin()
        }
        create_acc.setOnClickListener {
            val intent= Intent(this@LogInActivity,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun setupActionBar() {

        setSupportActionBar(toolbar_sign_in_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)
        }

        toolbar_sign_in_activity.setNavigationOnClickListener { onBackPressed() }

    }
    private fun signin(){
        // Here we get the text from editText and trim the space
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }
        if(validateForm(email,password)){
            val user=User(email,"",password)
            showProgressDialog("Please wait......")
            API().Login(user,this@LogInActivity)

            //API CALL STUFFS
        }


    }
    private fun validateForm(email: String, password: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            showErrorSnackBar("Please enter email.")
            false
        } else if (TextUtils.isEmpty(password)) {
            showErrorSnackBar("Please enter password.")
            false
        } else {
            true
        }
    }
    fun loggedinsucess(email:String){
        hideProgressDialog()
        val editor=mSharedPrefernces.edit()
        editor.putString("email",email)
        editor.apply()


        val intent=Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()




    }
}
//val retrofit:Retrofit=Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()