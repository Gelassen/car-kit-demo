package ru.autokit.android.screens.schedule

import ru.autokit.android.model.ScheduleItem
import ru.autokit.android.screens.BaseContract
import java.util.*

interface ScheduleContract {

    interface View: BaseContract.View {
        fun onData(data: List<ScheduleItem>)
        fun onNextStep()
    }

    interface Presenter {
        fun onStart(view: ScheduleContract.View, date: Long)
        fun onSubmitData(item: ScheduleItem)
    }
}