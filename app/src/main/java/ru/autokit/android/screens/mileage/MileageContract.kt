package ru.autokit.android.screens.mileage

import ru.autokit.android.model.CarSparesItem
import ru.autokit.android.model.ServiceDataItem
import ru.autokit.android.screens.BaseContract

interface MileageContract {

    interface View : BaseContract.View {

        fun openNextStep(result: List<ServiceDataItem>)

    }

    interface Presenter {

        fun onEnterMileage(entry: String)

    }
}