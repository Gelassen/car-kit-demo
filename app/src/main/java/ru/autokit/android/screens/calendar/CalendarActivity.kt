package ru.autokit.android.screens.calendar

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_calendar.*
import ru.autokit.android.AppApplication
import ru.autokit.android.R
import ru.autokit.android.screens.schedule.ScheduleActivity
import android.widget.Toast
import ru.autokit.android.screens.BaseActivity
import java.util.*
import javax.inject.Inject

class CalendarActivity: BaseActivity(), CalendarContract.View {

    @Inject
    lateinit var presenter: CalendarPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        (application as AppApplication).getComponent().inject(this)

        val nextFewMonths = Calendar.getInstance()
        nextFewMonths.add(Calendar.MONTH, 2)
        calendar_view.init(Date(), nextFewMonths.time)
            .withSelectedDate(Date())
        calendar_view.setOnDateSelectedListener(this)

        presenter.onStart(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressIndicator() {

    }

    override fun hideProgressIndicator() {

    }

    override fun onAvailableData(data: ArrayList<Long>) {
        calendar_view.decorators = listOf<CellDecorator>(CellDecorator(data))
    }

    override fun onDateSelected(date: Date) {
        presenter.onDateSelected(date)
    }

    override fun onDateUnselected(date: Date) {
        // no op
    }

    override fun onAvailableDateSelected(date: Date) {
        ScheduleActivity.launch(this, date)
    }
}