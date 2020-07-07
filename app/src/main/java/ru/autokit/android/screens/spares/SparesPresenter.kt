package ru.autokit.android.screens.spares

import io.reactivex.Scheduler
import ru.autokit.android.converters.SparesItemConverter
import ru.autokit.android.model.*
import ru.autokit.android.providers.OfferProvider
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.screens.BasePresenter
import javax.inject.Inject
import javax.inject.Named

class SparesPresenter @Inject
constructor(
    private val converter: SparesItemConverter,
    private val serviceProvider: ServiceProvider,
    private val offerProvider: OfferProvider,
    @param:Named("NETWORK") internal var schedulerNetwork: Scheduler,
    @param:Named("UI") internal var schedulerUI: Scheduler
)
    : BasePresenter<SparesContract.View>(), SparesContract.Presenter {

    override fun onPrepareData(model: List<ServiceDataItem>) {
        disposable.add(
            serviceProvider.getSpares(model)
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .doOnSubscribe{ it -> view.showProgressIndicator() }
                .doOnNext{ it -> view.hideProgressIndicator() }
                .doOnError{ it ->
                    view.hideProgressIndicator()
                    System.console().printf("Error: " + it.printStackTrace(), it.printStackTrace())
                    view.onError("Не удаётся обработать запрос. Попробуйте позже.")
                }
                .onErrorReturn { it ->
                    CarSpares(ArrayList<CarSparesItem>())
                }
                .subscribe {
                    if (it.result!!.isEmpty()) {
                        view.onError("Что-то пошло не так. Нет запчастей для вас, свяжитесь с владельцем сервиса")
                    } else {
                        view.onSubmitModel(converter.toViewModel(it.result!!))
                    }
                }
        )
    }

    override fun onSubmit(model: List<ViewCarSparesItem>) {
        // TODO cache data in system, upload on the server only on final step
        // TODO consider transform data to json over GSON and save in shared pref
        val model = converter.onlySelectedToModel(model)
        if (model.isEmpty()) {
            view.onError("Вы должны выбрать запчасти для замены")
        } else {
            serviceProvider.cacheSparesItems(model)
            view.openNextStep()
        }
    }

    override fun onListUpdate(model: MutableList<ViewCarSparesItem>) {
        val data = offerProvider.calculateTotalCostAndTime(model)
        view.onUpdateSummary(SummaryModel(data.first, data.second))
    }
}