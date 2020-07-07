package ru.autokit.android.screens.mileage

import android.util.Log
import io.reactivex.Scheduler
import ru.autokit.android.model.CarMileage
import ru.autokit.android.model.ServiceData
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.providers.ValidatorProvider
import ru.autokit.android.screens.BasePresenter
import javax.inject.Inject
import javax.inject.Named

class MileagePresenter @Inject
constructor(
    private val validatorProvider: ValidatorProvider,
    private val serviceProvider: ServiceProvider,
    @param:Named("NETWORK") internal var schedulerNetwork: Scheduler,
    @param:Named("UI") internal var schedulerUI: Scheduler
)
    : BasePresenter<MileageContract.View>(), MileageContract.Presenter {

    override fun onEnterMileage(entry: String) {
        val mileage = CarMileage(entry)
        if (validatorProvider.validate(mileage)) {
            getServices(mileage)
        } else {
            view.onError("Некорректное значение пробега. Должны быть только цифры не более 6 знаков")
        }
    }

    private fun getServices(mileage: CarMileage) {
        disposable.add(
            serviceProvider.getServices(mileage.entry)
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .doOnSubscribe{ it -> view.showProgressIndicator() }
                .doOnNext{ it -> view.hideProgressIndicator() }
                .doOnError{ it ->
                    Log.e("TAG", "OnError", it)
                    view.hideProgressIndicator()
                    view.onError("Не удаётся обработать запрос. Попробуйте позже или введите доп данные")
                }
                .onErrorReturn {  it ->
                    ServiceData(emptyList())
                }
                .subscribe { it->
                    if (it.result!!.isEmpty()) {
                        view.onError("Всё в порядке. Вам пока не нужно ничего менять")
                    } else {
                        view.openNextStep(it.result!!)
                    }
                }
        )
    }
}