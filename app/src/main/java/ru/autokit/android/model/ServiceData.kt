package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

data class ServiceData(@SerializedName("result") var result: List<ServiceDataItem>?)
