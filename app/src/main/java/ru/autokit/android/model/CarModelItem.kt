package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

data class CarModelItem(@SerializedName("model") val carModel: String?) {

    fun isEmpty(): Boolean {
        return carModel == null
    }
}