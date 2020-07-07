package ru.autokit.android.screens.intro

import ru.autokit.android.screens.BaseContract

interface IntroContract {

    interface View : BaseContract.View {

        fun openNextStep()

        fun showVehicleHandInputSection(
            carMake: String,
            carModel: String,
            carYear: String
        )

        fun onMakesDatasource(model: List<String>)

        fun onModelDatasource(model: List<String>)
    }

    interface Presenter {

        fun onStart(view: View)

        fun onEnterVIN(entry: String)

        fun onEnterVehicle(carMake: String?, carModel: String?, carYear: String)

    }
}