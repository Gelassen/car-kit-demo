package ru.autokit.android.screens.calendar

import com.squareup.timessquare.CalendarPickerView
import ru.autokit.android.model.ScheduleDateItem
import ru.autokit.android.screens.BaseContract
import java.util.*
import kotlin.collections.ArrayList

interface CalendarContract {

    interface View: BaseContract.View, CalendarPickerView.OnDateSelectedListener {

        fun onAvailableData(data: ArrayList<Long>)

        fun onAvailableDateSelected(date: Date)
    }

    interface Presenter {

        fun onStart(view: CalendarContract.View)

    }
}