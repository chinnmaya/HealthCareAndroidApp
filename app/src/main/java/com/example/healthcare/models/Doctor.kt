package com.example.healthcare.models

data class Doctor(
    val name:String,
    val specialist:String,

    val clinc:String,
    val email:String,
    val about:String,
    val profileimg:String,
    var slot10_11:Boolean,
    var slot11_12:Boolean,
    var slot1_2:Boolean,
    var slot2_3: Boolean,
    val ishomvisit: Boolean

):java.io.Serializable

