package ru.autokit.android.screens.submitform

import android.content.Context
import io.reactivex.Observable
import org.junit.Before
import ru.autokit.android.BaseTest

import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RuntimeEnvironment
import ru.autokit.android.di.DaggerTestComponent
import ru.autokit.android.di.Module
import ru.autokit.android.di.NetworkModule
import ru.autokit.android.model.Form
import ru.autokit.android.model.ScheduleItem
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.providers.ValidatorProvider
import ru.autokit.android.repository.CacheRepository
import ru.autokit.android.repository.ServiceRepository
import javax.inject.Inject

class SubmitPresenterTest : BaseTest() {

    internal inner class TestModule(context: Context): Module(context) {

        override fun providesVinValidator(): ValidatorProvider {
            return validatorProvider
        }

        override fun providesServiceProvider(
            serviceRepository: ServiceRepository,
            cacheRepository: CacheRepository
        ): ServiceProvider {
            return serviceProvider
        }

    }

    @Mock
    lateinit var validatorProvider: ValidatorProvider

    @Mock
    lateinit var serviceProvider: ServiceProvider

    @Mock
    lateinit var view: SubmitContract.View

    @Inject
    lateinit var subject: SubmitPresenter

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)

        val app = RuntimeEnvironment.application

        component = DaggerTestComponent.builder()
            .module(TestModule(app))
            .networkModule(TestNetworkModule(serverRule.getUrl().toString(), app))
            .build()

        component.inject(this)

        Mockito.`when`(serviceProvider.getCachedSpares())
            .thenReturn(Observable.just(emptyList()))
        Mockito.`when`(serviceProvider.getCachedSchedule())
            .thenReturn(Observable.just(ScheduleItem("", "")))
    }

    @Test
    fun onStart_dataInCache_getCachedFields() {
        subject.onStart(view)

        verify(serviceProvider).getCachedSpares()
        verify(serviceProvider).getCachedSchedule()
    }

    @Test
    fun onSubmitForm_invalidData_showError() {
        Mockito.`when`(validatorProvider.validate(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(false)
        subject.onStart(view)

        subject.onSubmitForm("", "")

        verify(view).onError(ArgumentMatchers.anyString())
        verify(view, never()).showSuccess()
    }

    @Test
    fun onSubmitForm_validData_showSuccess() {
        Mockito.`when`(validatorProvider.validate(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(true)
        Mockito.`when`(serviceProvider.pushSubmitForm(any(Form::class.java)))
            .thenReturn(Observable.just(Form()))
        subject.onStart(view)

        subject.onSubmitForm("", "")

        verify(view).showSuccess()
    }

    private fun <T> any(type: Class<T>): T {
        Mockito.any<T>(type)
        return uninitialized()
    }    private fun <T> uninitialized(): T = null as T
}