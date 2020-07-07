package ru.autokit.android.screens.schedule

import io.reactivex.Scheduler
import ru.autokit.android.model.ScheduleItem
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.screens.BasePresenter
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class SchedulePresenter @Inject
constructor(
    private val serviceProvider: ServiceProvider,
    @param:Named("NETWORK") internal var schedulerNetwork: Scheduler,
    @param:Named("UI") internal var schedulerUI: Scheduler
)
    : BasePresenter<ScheduleContract.View>(), ScheduleContract.Presenter {

    override fun onStart(view: ScheduleContract.View, date: Long) {
        super.onStart(view)
        start(date)
    }

    override fun onSubmitData(item: ScheduleItem) {
        serviceProvider.cacheScheduleItem(item)
        view.onNextStep()
    }

    private fun start(date: Long) {
        disposable.add(
            serviceProvider
                .getSchedule(date)
                .doOnError { view.onError("Не удалось получить данные с сервера") }
                .onErrorReturn {  ArrayList<ScheduleItem>() }
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .subscribe { it ->
                    view.onData(it) // show message if list is empty
                }
        )
    }
}