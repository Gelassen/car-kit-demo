package ru.autokit.android.screens.schedule

import android.content.Context
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Before
import ru.autokit.android.BaseTest

import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RuntimeEnvironment
import ru.autokit.android.di.DaggerTestComponent
import ru.autokit.android.di.Module
import ru.autokit.android.model.ScheduleItem
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.providers.ValidatorProvider
import ru.autokit.android.repository.CacheRepository
import ru.autokit.android.repository.ServiceRepository
import ru.autokit.android.screens.submitform.SubmitContract
import ru.autokit.android.screens.submitform.SubmitPresenter
import java.util.*
import javax.inject.Inject

class SchedulePresenterTest : BaseTest() {

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
    lateinit var view: ScheduleContract.View

    @Inject
    lateinit var subject: SchedulePresenter

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
    fun onStart_default_callOnData() {
        val date = Date().time
        Mockito.`when`(serviceProvider.getSchedule(date))
            .thenReturn(Observable.just(emptyList<ScheduleItem>()))

        subject.onStart(view, date)

        verify(view).onData(ArgumentMatchers.anyList())
    }

    @Test
    fun onSubmitData_item_cacheData() {
        val date = Date().time
        Mockito.`when`(serviceProvider.getSchedule(date))
            .thenReturn(Observable.just(emptyList<ScheduleItem>()))
        subject.onStart(view, date)

        subject.onSubmitData(ScheduleItem("", ""))

        verify(serviceProvider).cacheScheduleItem(any(ScheduleItem::class.java))
        verify(view).onNextStep()
    }

    private fun <T> any(type: Class<T>): T {
        Mockito.any<T>(type)
        return uninitialized()
    }    private fun <T> uninitialized(): T = null as T
}