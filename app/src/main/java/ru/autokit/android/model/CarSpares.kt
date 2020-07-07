package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

data class CarSpares(@SerializedName("result") var result: List<CarSparesItem>?)