package com.example.healthcare.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.healthcare.APICalls.API
import com.example.healthcare.R
import com.example.healthcare.models.Result
import com.example.healthcare.models.User

import com.example.healthcare.utils.BaseActivity
import com.example.healthcare.utils.Constants
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_log_in.et_email
import kotlinx.android.synthetic.main.activity_log_in.et_password
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.w3c.dom.Text
import retrofit.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupActionBar()
        btn_sign_up.setOnClickListener {
            signup()
        }
        acc_in.setOnClickListener {
            val intent=Intent(this@SignUpActivity,LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun setupActionBar() {

        setSupportActionBar(toolbar_sign_up_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)
        }

        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }

    }
    private fun signup(){
        // Here we get the text from editText and trim the space
        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }
        if(validateForm(name,email,password)){
            //API CALL STUFFS
            val user=User(email,name,password)
            showProgressDialog("Please wait....")
            API().registerUser(user,this@SignUpActivity)

        }


    }
    private fun validateForm(name:String,email: String, password: String): Boolean {
        return if (TextUtils.isEmpty(name)){
            showErrorSnackBar("Please enter your name")
            false
        }
        else if (TextUtils.isEmpty(email)) {
            showErrorSnackBar("Please enter email.")
            false
        } else if (TextUtils.isEmpty(password)) {
            showErrorSnackBar("Please enter password.")
            false
        } else {
            true
        }
    }
    fun sigin(){
        hideProgressDialog()
        Toast.makeText(this@SignUpActivity,"Registered Sucessfully!! Go for Login",Toast.LENGTH_LONG).show()
        val intent=Intent(this@SignUpActivity,LogInActivity::class.java)
        startActivity(intent)
        finish()
    }
}