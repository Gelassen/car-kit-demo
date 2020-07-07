package ru.autokit.android.screens.offers

import org.junit.Before
import org.junit.Ignore

import ru.autokit.android.BaseTest

import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import ru.autokit.android.model.ServiceDataItem
import ru.autokit.android.model.ViewServiceDataItem
import javax.inject.Inject

class OffersPresenterTest : BaseTest() {

    @Mock
    lateinit var view: OffersContract.View

    @Inject
    lateinit var subject: OffersPresenter

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)

        component.inject(this)
    }

    @Ignore
    @Test
    fun onStart_requestData_showData() {
        serverRule.getDispatcher().setOkServicesResponse()

        subject.onStart(view)

        verify(view).hideProgressIndicator()
        verify(view).showData(ArgumentMatchers.anyList<ViewServiceDataItem>())
    }

    @Ignore
    @Test
    fun onStart_requestDataAndEmptyModel_showData() {
        serverRule.getDispatcher().setOkEmptyServicesResponse()

        subject.onStart(view)

        verify(view).showEmptyScreen()
    }

    @Test
    fun onClick_openNextScreen() {
        serverRule.getDispatcher().setOkServicesResponse()
        subject.onStart(view)
        val item = ViewServiceDataItem(ServiceDataItem("",""))
        item.isSelected = true

        subject.onClick(listOf(item))

        verify(view).openNextScreen(ArgumentMatchers.anyList())
    }

    private fun <T> any(type: Class<T>): T {
        Mockito.any<T>(type)
        return uninitialized()
    }    private fun <T> uninitialized(): T = null as T
}