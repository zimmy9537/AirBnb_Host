package com.zimmy.best.airbnbhost.model

data class DatePair(
    val firstDate: DateBnb?,
    val secondDate: DateBnb?
) {
    constructor() : this(null, null)
}