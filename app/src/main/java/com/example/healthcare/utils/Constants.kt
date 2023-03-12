package com.example.healthcare.utils

import com.example.healthcare.APICalls.API
import com.example.healthcare.R
import com.example.healthcare.models.Doctor

object Constants {
     const val BASE_URL="http://172.29.10.177:9093"
     fun getdiseaseList():ArrayList<String>{
          // create an arraylist of type employee class
          val dieaselist=ArrayList<String>()
          dieaselist.add("Tumor Detection")
          dieaselist.add("Phenumonia Detection")
          dieaselist.add("Tumor Detection")
          dieaselist.add("Breast Cancer Detection")


          return  dieaselist
     }
     lateinit var doctorlist:ArrayList<Doctor>
     fun setdoctorlist(doctorlistrecieve:ArrayList<Doctor>){
          doctorlist=doctorlistrecieve

     }

     fun getdoctorList(): ArrayList<Doctor> {
          // create an arraylist of type employee class
          var doctorlist1=ArrayList<Doctor>()
         // doctorlist1=API().getdoctorslist()
          //print("${doctorlist1[0].name}")
         doctorlist1.add(Doctor("Faiz Kauzer","Tumor","dkl","xyz","xyz","hugh"))


          return  doctorlist1
     }

}