package com.example.healthcare.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.healthcare.R
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        signin.setOnClickListener {
            val intent1=Intent(this@IntroActivity,LogInActivity::class.java)
            startActivity(intent1)
        }
        signup.setOnClickListener {
            val intent2=Intent(this@IntroActivity,SignUpActivity::class.java)
            startActivity(intent2)
        }


    }
}