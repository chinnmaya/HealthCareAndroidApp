package com.example.healthcare.models

data class Doctor(
     val name:String,
     val specialist:String,

     val clinc:String,
     val email:String,
     val about:String,
     val profileimg:String,
     val slot10_11:Boolean,
     val slot11_12:Boolean,
     val slot1_2:Boolean,
     val slot2_3: Boolean,
     val ishomvisit: Boolean

):java.io.Serializable

