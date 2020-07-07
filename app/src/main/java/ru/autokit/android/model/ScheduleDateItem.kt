package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

data class ScheduleDateItem(
    @SerializedName("id") var id: String?,
    @SerializedName("time") var time: Long?
)
