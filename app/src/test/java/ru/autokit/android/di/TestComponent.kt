package ru.autokit.android.di

import dagger.Component
import ru.autokit.android.providers.ServiceProviderTest
import ru.autokit.android.screens.intro.IntroPresenterTest
import ru.autokit.android.screens.mileage.MileagePresenterTest
import ru.autokit.android.screens.offers.OffersPresenterTest
import ru.autokit.android.screens.schedule.SchedulePresenterTest
import ru.autokit.android.screens.spares.SparesPresenterTest
import ru.autokit.android.screens.submitform.SubmitPresenterTest
import javax.inject.Singleton

@Singleton
@Component(modules = [Module::class, NetworkModule::class])
interface TestComponent {

    fun inject(introPresenterTest: IntroPresenterTest)

    fun inject(introPresenterTest: MileagePresenterTest)

    fun inject(serviceProviderTest: ServiceProviderTest)

    fun inject(sparesPresenterTest: SparesPresenterTest)

    fun inject(submitPresenterTest: SubmitPresenterTest)

    fun inject(schedulePresenterTest: SchedulePresenterTest)

    fun inject(offersPresenterTest: OffersPresenterTest)

}