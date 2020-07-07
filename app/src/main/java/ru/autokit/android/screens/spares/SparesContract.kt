package ru.autokit.android.screens.spares

import ru.autokit.android.model.ServiceDataItem
import ru.autokit.android.model.SummaryModel
import ru.autokit.android.model.ViewCarSparesItem
import ru.autokit.android.screens.BaseContract

interface SparesContract {

    interface View: BaseContract.View {

        fun onSubmitModel(model: List<ViewCarSparesItem>)

        fun openNextStep()

        fun onUpdateSummary(model: SummaryModel)
    }

    interface Presenter {

        fun onPrepareData(model: List<ServiceDataItem>)

        fun onSubmit(model: List<ViewCarSparesItem>)

        fun onListUpdate(item: MutableList<ViewCarSparesItem>)
    }
}