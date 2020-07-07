package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

data class CarMake(@SerializedName("result") var result: List<CarMakeItem>?) {
}