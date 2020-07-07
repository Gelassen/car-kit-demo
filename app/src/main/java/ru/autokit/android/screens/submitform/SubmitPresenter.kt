package ru.autokit.android.screens.submitform

import io.reactivex.Scheduler
import ru.autokit.android.model.Form
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.providers.ValidatorProvider
import ru.autokit.android.screens.BasePresenter
import java.text.Normalizer
import javax.inject.Inject
import javax.inject.Named

class SubmitPresenter @Inject
constructor(
    private val serviceProvider: ServiceProvider,
    private val validator: ValidatorProvider,
    @param:Named("NETWORK") internal var schedulerNetwork: Scheduler,
    @param:Named("UI") internal var schedulerUI: Scheduler
)
    : BasePresenter<SubmitContract.View>(), SubmitContract.Presenter {

    private lateinit var form: Form

    override fun onStart(view: SubmitContract.View) {
        super.onStart(view)
        form = Form()

        // collect cached data
        getCachedSpares()
        getCachedSchedule()
    }

    override fun onSubmitForm(phone: String?, email: String?) {
        if (validator.validate(phone, email)) {
            form.phone = phone
            form.email = email
            submitForm()
        } else {
            view.onError("Неверный почтовый адрес или телефонный номер. Проверьте введённые данные.")
        }
    }

    private fun getCachedSpares() {
        disposable.add(
            serviceProvider.getCachedSpares()
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .subscribe {
                    form.spares = it
                }
        )
    }

    private fun getCachedSchedule() {
        disposable.add(
            serviceProvider.getCachedSchedule()
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .subscribe {
                    form.schedule = it
                }
        )
    }

    private fun submitForm() {
        disposable.add(
            serviceProvider.pushSubmitForm(form)
                .subscribeOn(schedulerNetwork)
                .observeOn(schedulerUI)
                .subscribe {
                    if (it != null) {
                        view.showSuccess()
                    } else {
                        view.onError("Не удалось создать заявку. Свяжитесь с оператором.")
                    }
                }
        )
    }
}