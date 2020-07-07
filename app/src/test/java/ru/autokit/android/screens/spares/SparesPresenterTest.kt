package ru.autokit.android.screens.spares

import android.content.Context
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import ru.autokit.android.BaseTest

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RuntimeEnvironment
import ru.autokit.android.di.DaggerTestComponent
import ru.autokit.android.di.Module
import ru.autokit.android.model.CarSparesItem
import ru.autokit.android.model.ServiceDataItem
import ru.autokit.android.model.ViewCarSparesItem
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.repository.CacheRepository
import ru.autokit.android.repository.ServiceRepository
import javax.inject.Inject

class SparesPresenterTest : BaseTest() {

    internal inner class TestModule(context: Context): Module(context) {

        override fun providesServiceProvider(
            serviceRepository: ServiceRepository,
            cacheRepository: CacheRepository
        ): ServiceProvider {
            return serviceProvider
        }

    }

    @Mock
    lateinit var serviceProvider: ServiceProvider

    @Mock
    lateinit var view: SparesContract.View

    @Inject
    lateinit var subject: SparesPresenter

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

        subject.onStart(view)
    }

    @Test
    fun testOnStart_requestSparesData_showData() {
        serverRule.getDispatcher().setOkSparesResponse()

        subject.onPrepareData(listOf(ServiceDataItem("10","fix")))

        verify(view).onSubmitModel(ArgumentMatchers.anyList())
    }


    @Test
    fun testOnSubmit_emptyModel_callOnError() {
        val model = ArrayList<ViewCarSparesItem>()

        subject.onSubmit(model)

        verify(view).onError("Вы должны выбрать запчасти для замены")
    }

    @Test
    fun testOnSubmit_fullModel_savedModelIsValid() {
        val model = ArrayList<ViewCarSparesItem>()
        val validItem = ViewCarSparesItem(CarSparesItem("item", "10", "20", "", "", ""))
        validItem.isOriginSelected = true
        model.add(validItem)
        val invalidItem = ViewCarSparesItem(CarSparesItem("item2", "100", "200", "", "", ""))
        model.add(invalidItem)

        subject.onSubmit(model)

        verify(view, never()).onError(ArgumentMatchers.anyString())
        verify(view).openNextStep()
    }

    private fun <T> any(type: Class<T>): T {
        Mockito.any<T>(type)
        return uninitialized()
    }    private fun <T> uninitialized(): T = null as T
}