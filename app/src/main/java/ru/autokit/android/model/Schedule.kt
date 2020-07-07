package ru.autokit.android.model

import com.google.gson.annotations.SerializedName

data class Schedule(@SerializedName("result") var result: List<ScheduleItem>?)