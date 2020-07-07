package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

class Vehicle {

    @SerializedName("carMake")
    var carMake: String? = ""

    @SerializedName("carModel")
    var carModel: String? = ""


    @SerializedName("carYear")
    var carYear: String? = ""

    var makeItem: CarMakeItem? = null

    var modelItem: CarModelItem? = null

    constructor()

    constructor(carMake: String?, carModel: String?, carYear: String?) {
        this.carMake = carMake
        this.carModel = carModel
        this.carYear = carYear
    }

    fun isEmpty(): Boolean {
        return isEmpty(carMake)
                && isEmpty(carModel)
    }

    private fun isEmpty(entry: String?): Boolean {
        return entry == null || entry.equals("")
    }
}