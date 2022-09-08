package com.zimmy.best.airbnbhost

data class User(val name: String?, val email: String?) {
    constructor() : this(null, null)
}