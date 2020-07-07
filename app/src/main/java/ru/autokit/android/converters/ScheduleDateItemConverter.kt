package ru.autokit.android.converters

import ru.autokit.android.model.ScheduleDateItem

class ScheduleDateItemConverter {

    fun toLongList(data: List<ScheduleDateItem>): List<Long> {
        val result = ArrayList<Long>()
        for (item in data) {
            result.add(item.time!!)
        }
        return result
    }
}