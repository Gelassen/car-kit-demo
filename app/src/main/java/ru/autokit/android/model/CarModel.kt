package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

data class CarModel(@SerializedName("result") var result: List<CarModelItem>?)