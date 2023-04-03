package com.example.healthcare.models

data class User(
    val  email:String,
    val name:String,
    val password:String,
    var money:Int,
):java.io.Serializable
