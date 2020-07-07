package ru.autokit.android.screens.calendar

import io.reactivex.Scheduler
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.screens.BasePresenter
import javax.inject.Inject
import javax.inject.Named
import ru.autokit.android.converters.ScheduleDateItemConverter
import ru.autokit.android.providers.ValidatorProvider
import java.util.*
import kotlin.collections.ArrayList

class CalendarPresenter @Inject
constructor(
    private val serviceProvider: ServiceProvider,
    @param:Named("NETWORK") internal var schedulerNetwork: Scheduler,
    @param:Named("UI") internal var schedulerUI: Scheduler
)
    : BasePresenter<CalendarContract.View>(), CalendarContract.Presenter {

    var availableDates = ArrayList<Long>()

    override fun onStart(view: CalendarContract.View) {
        this.view = view

        disposable.add(
            serviceProvider.getAvailableDates()
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .subscribe() { it ->
                    availableDates = ScheduleDateItemConverter().toLongList(it.result!!) as ArrayList<Long>
                    view.onAvailableData(availableDates)
                }
        )
    }

    fun onDateSelected(date: Date) {
        if (ValidatorProvider().validate(date.time, availableDates)) {
            view.onAvailableDateSelected(date)
        } else {
            view.onError("Выбранная дата недоступна")
        }
    }

}