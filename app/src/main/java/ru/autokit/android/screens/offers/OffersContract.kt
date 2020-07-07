package ru.autokit.android.screens.offers

import ru.autokit.android.model.ServiceDataItem
import ru.autokit.android.model.ViewServiceDataItem
import ru.autokit.android.screens.BaseContract

interface OffersContract: BaseContract {

    interface View: BaseContract.View {

        fun showData(list: List<ViewServiceDataItem>)

        fun openNextScreen(serviceData: List<ServiceDataItem>)

        fun showEmptyScreen()
    }

    interface Presenter {

        fun onClick(serviceData: List<ViewServiceDataItem>)

        fun onPrepareData(data: List<ServiceDataItem>)
    }
}