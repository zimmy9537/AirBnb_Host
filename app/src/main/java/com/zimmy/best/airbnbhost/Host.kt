package com.zimmy.best.airbnbhost

data class Host(val name: String?, val email: String?) {
    constructor() : this(null, null)
}