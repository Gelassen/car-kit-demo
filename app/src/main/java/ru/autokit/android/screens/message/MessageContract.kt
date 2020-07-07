package ru.autokit.android.screens.message

import ru.autokit.android.screens.BaseContract

class MessageContract {

    interface View : BaseContract.View

    interface Presenter {

        fun onSubmit()

    }
}
