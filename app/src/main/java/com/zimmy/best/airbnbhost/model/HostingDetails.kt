package com.zimmy.best.airbnbhost.model

data class HostingDetails(
    var roomList: ArrayList<String>?,
    var detailMap: HashMap<String, Boolean>?,
    var photoList: ArrayList<String>?,
    var availableFrom: DateBnb?
) {
    constructor(
        roomList: ArrayList<String>,
        detailMap: HashMap<String, Boolean>,
        photoList: ArrayList<String>
    ) : this(roomList, detailMap, photoList, null)

    constructor() : this(null, null, null, null)
}
