package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

data class CarMakeItem(@SerializedName("make") val carMake: String?) {

    fun isEmpty(): Boolean {
        return carMake == null
    }
}