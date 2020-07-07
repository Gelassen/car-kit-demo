package ru.autokit.android.screens.offers

import ru.autokit.android.converters.OffersConverter
import ru.autokit.android.model.ServiceDataItem
import ru.autokit.android.model.ViewServiceDataItem
import ru.autokit.android.screens.BasePresenter
import javax.inject.Inject

class OffersPresenter @Inject
constructor()
    : BasePresenter<OffersContract.View>(), OffersContract.Presenter {

    override fun onPrepareData(data: List<ServiceDataItem>) {
        view.showData(OffersConverter().toViewModel(data))
    }

    override fun onClick(serviceData: List<ViewServiceDataItem>) {
        val model = OffersConverter().toModelOfSelected(serviceData)
        if (model.isEmpty()) {
            view.onError("Вы должны выбрать нужный вам сервис перед тем как продолжить дальше")
        } else {
            view.openNextScreen(model)
        }
    }

}