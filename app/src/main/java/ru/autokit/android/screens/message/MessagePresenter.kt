package ru.autokit.android.screens.message

import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.screens.BasePresenter
import javax.inject.Inject

class MessagePresenter @Inject
constructor(
    val serviceProvider: ServiceProvider
)
    : BasePresenter<MessageContract.View>(), MessageContract.Presenter {

    override fun onSubmit() {
        serviceProvider.clean()
    }
}