package ru.autokit.android.converters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.autokit.android.model.ScheduleItem

class ScheduleItemConverter {

    fun toJson(model: ScheduleItem): String {
        return Gson().toJson(model)
    }

    fun fromJson(json: String): ScheduleItem {
        val type = TypeToken.get(ScheduleItem::class.java).type
        return Gson().fromJson(json, type)
    }
}