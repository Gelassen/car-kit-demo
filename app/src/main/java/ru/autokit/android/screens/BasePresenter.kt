package ru.autokit.android.screens

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T: BaseContract.View> {

    protected lateinit var view : T

    protected val disposable : CompositeDisposable = CompositeDisposable()

    open fun onStart(view: T) {
        this.view = view
    }

    fun onDestroy() {
        clear()
    }

    protected fun clear() {
        disposable.clear()
    }
}