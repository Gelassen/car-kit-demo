package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

data class ScheduleDate(@SerializedName("result") var result: List<ScheduleDateItem>?)