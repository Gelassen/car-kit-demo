package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

data class Form(
    @SerializedName("spares") var spares: List<CarSparesItem>? = emptyList(),
    @SerializedName("schedule") var schedule: ScheduleItem? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("email") var email: String? = null
)