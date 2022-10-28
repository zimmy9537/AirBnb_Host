package com.zimmy.best.airbnbhost.model

data class BookingDetails(
    var datePair: DatePair?,
    var user_uid: String,
    var user_name: String,
    var guest: Guest?,
    var booking_id: String,
    var user_phone: String,
    var hosting_id: String,
    var hostingDetail: String
) {
    constructor() : this(null, "", "", null, "", "", "", "")
}