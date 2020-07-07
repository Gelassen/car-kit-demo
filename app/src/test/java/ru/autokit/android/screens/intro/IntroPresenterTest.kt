package ru.autokit.android.screens.intro

import android.content.Context
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RuntimeEnvironment
import ru.autokit.android.BaseTest
import ru.autokit.android.di.DaggerTestComponent
import ru.autokit.android.di.Module
import ru.autokit.android.model.Vehicle
import ru.autokit.android.providers.ValidatorProvider
import javax.inject.Inject

class IntroPresenterTest: BaseTest() {

    internal inner class TestModule(context: Context) : Module(context) {

        override fun providesVinValidator(): ValidatorProvider {
            return vinValidatorProvider
        }

    }

    @Mock
    internal var vinValidatorProvider = mock(ValidatorProvider::class.java)

    @Mock
    lateinit var view: IntroContract.View

    @Inject
    lateinit var subject: IntroPresenter

    @Before
    override fun setUp() {
        super.setUp()
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
    fun onEnterNotValidVINTest() {
        Mockito.`when`(vinValidatorProvider.validate(anyString()))
            .thenReturn(false)
        val entry = "3B3ES47C6WT211725"

        subject.onEnterVIN(entry)

        verify(vinValidatorProvider).validate(entry)
        verify(view).onError("Введённый VIN некорректен")
    }

    @Test
    fun onEnterValidVINTest() {
        Mockito.`when`(vinValidatorProvider.validate(anyString()))
            .thenReturn(true)
        serverRule.getDispatcher().setOkVehicleResponse()
        val entry = "1J4GZ58S7VC697710"

        subject.onEnterVIN(entry)

        verify(vinValidatorProvider).validate(entry)

        verify(view).showVehicleHandInputSection("Nissan", "Juke", "")
        verify(view).onModelDatasource(ArgumentMatchers.anyList())
        verify(view).onMakesDatasource(ArgumentMatchers.anyList())
        verify(view, never()).openNextStep()
    }


    @Test
    fun onEnterValidVINAndVehicleNotFoundTest() {
        Mockito.`when`(vinValidatorProvider.validate(anyString()))
            .thenReturn(true)
        serverRule.getDispatcher().setNotFoundVehicleResponse()
        val entry = "3B3ES47C6WT2117"

        subject.onEnterVIN(entry)

        verify(vinValidatorProvider).validate(entry)
        verify(view).showVehicleHandInputSection("", "", "")
        verify(view).onModelDatasource(ArgumentMatchers.anyList())
        verify(view).onMakesDatasource(ArgumentMatchers.anyList())
        verify(view, never()).openNextStep()
    }

    @Test
    fun onEnterVehicle_validVehicle_openNext() {
        Mockito.`when`(vinValidatorProvider.validate(any(Vehicle::class.java)))
            .thenReturn(true)

        subject.onEnterVehicle("Nissan", "Juke", 2015.toString())

        verify(view).openNextStep()
    }

    @Test
    fun onEnterVehicle_invalidVehicle_callOnError() {
        Mockito.`when`(vinValidatorProvider.validate(any(Vehicle::class.java)))
            .thenReturn(false)

        subject.onEnterVehicle("Nissan", null, 2015.toString())

        verify(view).onError(anyString())
    }

    @Test
    fun onEnterVin_validVin_showVehicleHandInputSection() {
        Mockito.`when`(vinValidatorProvider!!.validate(anyString()))
            .thenReturn(true)
        serverRule.getDispatcher().setOkVehicleResponse()
        val vin = "1J4GZ58S7VC697710"

        subject!!.onEnterVIN(vin)

        verify<ValidatorProvider>(vinValidatorProvider).validate(vin)
        verify(view, never()).openNextStep()
        verify(view).onModelDatasource(ArgumentMatchers.anyList<String>())
        verify(view).onMakesDatasource(ArgumentMatchers.anyList<String>())
//        verify(view).showVehicleHandInputSection("Nissan", "Juke", "")
    }

    @Test
    fun onEnterVin_validVinAndVinNotFound_showVehicleHandInputSection() {
        Mockito.`when`(vinValidatorProvider!!.validate(anyString()))
            .thenReturn(true)
        serverRule.getDispatcher().setOkVehicleResponse()
        val vin = "1J4GZ58S7VC697710"

        subject!!.onEnterVIN(vin)

        verify<ValidatorProvider>(vinValidatorProvider).validate(vin)
        verify(view, never()).openNextStep()
        verify(view).onModelDatasource(ArgumentMatchers.anyList<String>())
        verify(view).onMakesDatasource(ArgumentMatchers.anyList<String>())
//        verify(view).showVehicleHandInputSection("", "", "")
    }

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
//     */
//    fun <T> any(): T = Mockito.any<T>()

//    inline fun <reified T : Any> any() = Mockito.any(Vehicle::class.java) ?: Vehicle()

    private fun <T> any(type: Class<T>): T {
        Mockito.any<T>(type)
        return uninitialized()
    }    private fun <T> uninitialized(): T = null as T

}