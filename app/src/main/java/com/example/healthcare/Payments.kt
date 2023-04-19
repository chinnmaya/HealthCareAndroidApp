package com.example.healthcare

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.healthcare.APICalls.API
import com.example.healthcare.Activity.PaymentActivity
import com.example.healthcare.models.User
import com.example.healthcare.utils.Constants
import com.razorpay.Checkout
import kotlinx.android.synthetic.main.fragment_payments.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Payments.newInstance] factory method to
 * create an instance of this fragment.
 */
class Payments : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var muser:User?=null
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //startActivity(Intent(context,PaymentActivity::class.java))
        val view=inflater.inflate(R.layout.fragment_payments, container, false)
        progressBar = view.findViewById(R.id.progressBar)

        val user = arguments?.getSerializable(Constants.PAYMENTUSER) as User
        progressBar.visibility = View.VISIBLE

        API().getUserbyId(user.email){user->
            muser=user
            val tvMoney=view.findViewById<TextView>(R.id.tvMoney)
            tvMoney.setText(muser!!.money.toString())
            progressBar.visibility = View.GONE

        }



        val bundle = Bundle()
        val addmoney = view.findViewById<Button>(R.id.addmoney)
        addmoney.setOnClickListener {
            // val amount = et_amount.text.toString()
            //intent.putExtra(Constants.AMOUNT,amount)
            val et_amount=view.findViewById<EditText>(R.id.et_amount)
            val amount=et_amount.text.toString()
            if(TextUtils.isEmpty(amount)) {
                Toast.makeText(activity,"Enter an amount",Toast.LENGTH_SHORT).show()
            }else {
                bundle.putString(Constants.AMOUNT, amount)
                val intent = Intent(activity, PaymentActivity::class.java)
                intent.putExtras(bundle)
                intent.putExtra("pass", muser)

                startActivity(intent)
            }
        }

    return view
}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Payments.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Payments().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}