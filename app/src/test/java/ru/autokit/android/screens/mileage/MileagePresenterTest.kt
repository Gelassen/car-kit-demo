package ru.autokit.android.screens.mileage

import android.content.Context
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RuntimeEnvironment
import ru.autokit.android.BaseTest
import ru.autokit.android.di.DaggerTestComponent
import ru.autokit.android.di.Module
import ru.autokit.android.model.CarMileage
import ru.autokit.android.providers.ValidatorProvider
import javax.inject.Inject

class MileagePresenterTest: BaseTest() {

    internal inner class TestModule(context: Context) : Module(context) {

        override fun providesVinValidator(): ValidatorProvider {
            return vinValidatorProvider
        }
    }

    @Mock
    internal var vinValidatorProvider = mock(ValidatorProvider::class.java)

    @Mock
    lateinit var view: MileageContract.View

    @Inject
    lateinit var subject: MileagePresenter

    override fun setUp() {
        MockitoAnnotations.initMocks(this)

        val app = RuntimeEnvironment.application

        component = DaggerTestComponent
            .builder()
            .module(TestModule(app))
            .networkModule(TestNetworkModule(serverRule.getUrl().toString(), app))
            .build()

        component.inject(this)
        subject.onStart(view)
    }

    @Test
    fun onEnterMileage_notValid_showError() {
        val carMileage = CarMileage("10000")
        Mockito.`when`(vinValidatorProvider.validate(carMileage))
            .thenReturn(false)

        subject.onEnterMileage(10000.toString())

        verify(view).onError("Некорректное значение пробега. Должны быть только цифры не более 6 знаков")
    }

    @Test
    fun onEnterMileage_validEntryFull_openNextScreenWithItems() {
        Mockito.`when`(vinValidatorProvider.validate(any(CarMileage::class.java)))
            .thenReturn(true)

        subject.onEnterMileage("10000")

        verify(view).openNextStep(ArgumentMatchers.anyList())
    }

    @Test
    fun onEnterMileage_validEntryEmpty_showMessage() {
        Mockito.`when`(vinValidatorProvider.validate(any(CarMileage::class.java)))
            .thenReturn(true)
        serverRule.getDispatcher().setOkEmptyServicesResponse()

        subject.onEnterMileage("10000")

        verify(view).onError("Всё в порядке. Вам пока не нужно ничего менять")
    }

    @Test
    @Ignore
    fun onEnterMileage_validEntryAndServerIssue_showError() {
        Mockito.`when`(vinValidatorProvider.validate(any(CarMileage::class.java)))
            .thenReturn(true)
        serverRule.getDispatcher().setServerIssueSparesResponse()

        subject.onEnterMileage("10000")

        verify(view).onError("Не удаётся обработать запрос. Попробуйте позже.")
    }

    private fun <T> any(type: Class<T>): T {
        Mockito.any<T>(type)
        return uninitialized()
    }    private fun <T> uninitialized(): T = null as T
}