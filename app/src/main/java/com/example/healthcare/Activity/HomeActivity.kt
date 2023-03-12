package com.example.healthcare.Activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.healthcare.APICalls.API
import com.example.healthcare.Account
import com.example.healthcare.Home
import com.example.healthcare.Payments
import com.example.healthcare.R
import com.example.healthcare.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() {
    private lateinit var mSharedPrefernces: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //showProgressDialog("wait")
        //API().getdoctorslist(this)


        mSharedPrefernces=getSharedPreferences("user", Context.MODE_PRIVATE)
      //  button.setOnClickListener {
          //  val editor=mSharedPrefernces.edit()
            //editor.putString("email","")
          //  editor.apply()

       // }
        replacefragment(Home())

        nav_view.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home->replacefragment(Home())
                R.id.payment->replacefragment(Payments())
                R.id.profile->replacefragment(Account())
                else->{

                }


            }
            true
        }

    }
    fun replacefragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransition=fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_layout,fragment)
        fragmentTransition.commit()
    }
}