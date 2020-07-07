package ru.autokit.android.screens.intro


import io.reactivex.Scheduler
import ru.autokit.android.model.CarMakeItem
import ru.autokit.android.model.CarModelItem
import ru.autokit.android.model.Vehicle
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.providers.ValidatorProvider
import ru.autokit.android.screens.BasePresenter
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class IntroPresenter @Inject
constructor(
    private val validatorProvider: ValidatorProvider,
    private val serviceProvider: ServiceProvider,
    @param:Named("NETWORK") internal var schedulerNetwork: Scheduler,
    @param:Named("UI") internal var schedulerUI: Scheduler
)
    : BasePresenter<IntroContract.View>(), IntroContract.Presenter {

    override fun onEnterVIN(entry: String) {
        if (validatorProvider.validate(entry)) {
            getVehicleByVIN(entry)
        } else {
            view.onError("Введённый VIN некорректен")
        }
    }

    override fun onEnterVehicle(carMake: String?, carModel: String?, carYear: String) {
        if (validatorProvider.validate(Vehicle(carMake, carModel, carYear))) {
            // TODO send year as well, blocked by unified API method
            sendCarMake(carMake, carModel)
        } else {
            view.onError("Некорректная информация о ТС. Пожалуйста, исправьте модель, марку или год и попробуйте ещё раз.")
        }
    }

    private fun getVehicleByVIN(entry: String) {
        disposable.add(
            serviceProvider.getVehicleByVIN(entry)
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .doOnSubscribe{ it -> view.showProgressIndicator() }
                .doOnNext{ it -> view.hideProgressIndicator() }
                .doOnError{ it ->
                    view.hideProgressIndicator()
                    view.onError("Не удаётся обработать запрос. Попробуйте позже или введите доп данные")
                }
                .onErrorReturn {  it ->
                    Vehicle()
                }
                .subscribe { model ->
                    getListOfMakes()
                    getListOfModels()
                    if (model.isEmpty()) {
                        view.showVehicleHandInputSection("", "", "")
                    } else {
                        view.showVehicleHandInputSection(model.carMake!!, model.carModel!!, model.carYear!!)
                    }
                }
        )
    }

    private fun getListOfMakes() {
        disposable.add(
            serviceProvider.getListOfMakes()
                .doOnError{ it ->
                    view.onError("Не удаётся обработать запрос. Попробуйте позже или введите доп данные")
                }
                .onErrorReturn {  it ->
                    Collections.emptyList()
                }
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .subscribe { it ->
                    view.onMakesDatasource(it)
                }
        )
    }

    private fun getListOfModels() {
        disposable.add(
            serviceProvider.getListOfModels()
                .doOnError{ it ->
                    view.onError("Не удаётся обработать запрос. Попробуйте позже или введите доп данные")
                }
                .onErrorReturn {  it ->
                    Collections.emptyList()
                }
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .subscribe { it ->
                    view.onModelDatasource(it)
                }
        )
    }

    private fun sendCarMake(carMake: String?, carModel: String?) {
        disposable.add(
            serviceProvider.pushCarMake(CarMakeItem(carMake))
                .doOnError { it -> view.onError("Не удалось отослать марку") } // TODO switch on UI thread, current implementation is called in another thread
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .subscribe { it ->
                    sendModel(CarModelItem(carModel))
                }
        )

    }

    private fun sendModel(modelItem: CarModelItem) {
        disposable.add(
            serviceProvider.pushModel(modelItem)
                .doOnError { it -> view.onError("Не удалось отослать модель") }
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .subscribe { it -> view.openNextStep() }
        )
    }

}