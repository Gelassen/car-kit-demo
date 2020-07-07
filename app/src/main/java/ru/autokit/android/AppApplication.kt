package ru.autokit.android

import android.app.Application
import ru.autokit.android.di.AppComponent
import ru.autokit.android.di.DaggerAppComponent
import ru.autokit.android.di.Module
import ru.autokit.android.di.NetworkModule

class AppApplication : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    fun getComponent(): AppComponent {
        return component
    }

    private fun initDagger() {
        component = DaggerAppComponent
            .builder()
            .module(Module(this))
            .networkModule(NetworkModule(getString(R.string.url), this))
            .build()
    }

}
