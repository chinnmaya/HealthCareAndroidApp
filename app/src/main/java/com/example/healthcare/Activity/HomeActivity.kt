package com.example.healthcare.Activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.healthcare.APICalls.API
import com.example.healthcare.Account
import com.example.healthcare.Home
import com.example.healthcare.Payments
import com.example.healthcare.R
import com.example.healthcare.fragments.Doctorprofile
import com.example.healthcare.models.User
import com.example.healthcare.utils.BaseActivity
import com.example.healthcare.utils.Constants
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() {
    private lateinit var mSharedPrefernces:SharedPreferences
   // private lateinit var muser:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //showProgressDialog("wait")
        //API().getdoctorslist(this)


      //  mSharedPrefernces=getSharedPreferences("user", Context.MODE_PRIVATE)
        //val email=mSharedPrefernces.getString("email","")
        //Toast.makeText(this,email,Toast.LENGTH_SHORT).show()
      //  button.setOnClickListener {
          //  val editor=mSharedPrefernces.edit()
            //editor.putString("email","")
          //  editor.apply()

       // }

        val bundle = Bundle()
        //getting the userId from the shared preference
        mSharedPrefernces = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        var email=mSharedPrefernces.getString("email","")
        if (email != null) {
            //getiting the user Object and strore it on a global varriable
            API().getUserbyId(email){user->
                bundle.putSerializable(Constants.PAYMENTUSER,user)
                //muser=user

            }
        }
        //put the User Serializable object to the fragment


        val paymentFragement = Payments()
        paymentFragement.arguments = bundle


        replacefragment(Home())


        nav_view.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home->replacefragment(Home())
                R.id.payment->replacefragment(paymentFragement)
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