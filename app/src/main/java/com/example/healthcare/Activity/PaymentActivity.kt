package com.example.healthcare.Activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.healthcare.APICalls.API
import com.example.healthcare.R
import com.example.healthcare.models.User
import com.example.healthcare.utils.Constants
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() , PaymentResultListener {
    lateinit var muser:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val bundle = intent.extras
        val amount = bundle?.getString(Constants.AMOUNT)

         val user = intent.getSerializableExtra("pass") as User
       // Toast.makeText(this,amount,Toast.LENGTH_SHORT).show()

        if (amount != null) {
            muser=user
            muser.money=muser.money+amount.toInt()
            Toast.makeText(this,amount,Toast.LENGTH_SHORT).show()

            startPayment(amount)
        }



    }
    private fun startPayment(amount:String) {
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()


        try {
            val options = JSONObject()
            options.put("name","HealthCare Corp")
            options.put("description","Add Money")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            //options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount",amount+"00")//pass amount in currency subunits




            val prefill = JSONObject()
            prefill.put("email",muser.email)
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        API().updateMoney(muser){}


    }

    override fun onPaymentError(p0: Int, p1: String?) {

    }
}