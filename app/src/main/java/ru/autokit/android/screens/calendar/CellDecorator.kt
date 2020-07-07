package ru.autokit.android.screens.calendar

import com.squareup.timessquare.CalendarCellDecorator
import com.squareup.timessquare.CalendarCellView
import ru.autokit.android.providers.ValidatorProvider
import java.util.*

class CellDecorator: CalendarCellDecorator {

    val validator = ValidatorProvider()
    var availableTime: ArrayList<Long>

    val paramCalendar = Calendar.getInstance()
    val compareCalendar = Calendar.getInstance()

    constructor(availableTime: ArrayList<Long>) {
        this.availableTime = availableTime
    }


    override fun decorate(cellView: CalendarCellView?, date: Date?) {
        // set selectable for view out of application range
        // set highlighted for view within application range
        cellView!!.isSelectable = false
        if (validator.validate(date!!.time, availableTime)) {
            cellView.isHighlighted = true
        }
    }

}