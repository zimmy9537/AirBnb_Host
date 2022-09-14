package com.zimmy.best.airbnbhost.model

import java.io.Serializable

data class BasicDetails(var option:Int,var title:String,var address:String,var longitude:String,var latitude:String,var price:Int): Serializable
