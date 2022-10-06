package com.zimmy.best.airbnbhost.model

data class HostingDetails(
    var roomList: ArrayList<String>?,
    var detailMap: HashMap<String, Boolean>?,
    var photoList: ArrayList<String>?
) {
    constructor() : this(null, null, null)
}