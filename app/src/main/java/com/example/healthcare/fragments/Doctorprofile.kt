package com.example.healthcare.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.healthcare.R
import com.example.healthcare.models.Doctor
import kotlinx.android.synthetic.main.fragment_doctorprofile.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Doctorprofile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Doctorprofile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_doctorprofile, container, false)
        val bundle = arguments
        val doctor = bundle?.getSerializable("doctor") as? Doctor

        if (doctor != null) {

            // use the doctor object
            val name = view.findViewById<TextView>(R.id.name)
            name?.text = doctor.name
            val specialist = view.findViewById<TextView>(R.id.specialist)
            specialist?.setText(doctor.specialist)
            val visit = view.findViewById<TextView>(R.id.visit)
            if(doctor.ishomvisit==true){
                visit?.setText("Avaliable for Home Visit")
            }else{
                visit?.setText("Avaliable for only Hospital Visit")
            }
            val address = view.findViewById<TextView>(R.id.address)
            address?.setText(doctor.clinc)
            val about=view.findViewById<TextView>(R.id.about)
            about?.setText(doctor.about)
            setSlot(doctor.slot10_11,doctor.slot11_12,doctor.slot1_2,doctor.slot2_3,view)
            val email_btn=view.findViewById<ImageView>(R.id.email)
            email_btn.setOnClickListener {
                //openingEmail(doctor.email)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:${doctor.email}"))
                startActivity(intent)
            }


        } else {
            Toast.makeText(context,"Not acess",Toast.LENGTH_SHORT).show()
            // handle the null case
        }




        // Inflate the layout for this fragment

        return view
    }





    //Setting the slots
    fun setSlot(slot1:Boolean,slot2:Boolean,slot3:Boolean,slot4:Boolean,view: View){

        val slot1tv=view.findViewById<TextView>(R.id.tv_slot1)
        val slot2tv=view.findViewById<TextView>(R.id.tv_slot2)
        val slot3tv=view.findViewById<TextView>(R.id.tv_slot3)
        val slot4tv=view.findViewById<TextView>(R.id.tv_slot4)
        //set slot1
        if(slot1==true){
            slot1tv.setText("Booked")

        }else{
            slot1tv.setText("10AM - 11AM")
        }
        if(slot2==true){
            slot2tv.setText("Booked")

        }else{
            slot2tv.setText("11.30AM - 12.30PM")
        }
        if(slot3==true){
            slot3tv.setText("Booked")

        }else{
            slot3tv.setText("12.30PM - 1.30PM")
        }
        if(slot4==true){
            slot4tv.setText("Booked")

        }else{
            slot4tv.setText("2PM - 3PM")
        }

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
         * @return A new instance of fragment Doctorprofile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Doctorprofile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}