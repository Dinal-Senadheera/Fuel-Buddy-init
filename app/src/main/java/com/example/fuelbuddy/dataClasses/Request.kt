package com.example.fuelbuddy.dataClasses

data class Request(var key:String ?= null, var Name:String ?= null, var Qty:Int ?= null,
                   var Description:String ?= null,var Post:String ?= null): java.io.Serializable
