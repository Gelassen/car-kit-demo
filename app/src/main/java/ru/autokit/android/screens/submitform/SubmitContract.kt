package ru.autokit.android.screens.submitform

import ru.autokit.android.screens.BaseContract

interface SubmitContract {

    interface View : BaseContract.View {
        fun showSuccess()
    }

    interface Presenter {
        fun onSubmitForm(phone: String?, email: String?)
    }
}
