package ru.autokit.android.di

import dagger.Component
import ru.autokit.android.screens.calendar.CalendarActivity
import ru.autokit.android.screens.intro.IntroActivity
import ru.autokit.android.screens.message.FinalScreen
import ru.autokit.android.screens.mileage.MileageActivity
import ru.autokit.android.screens.offers.OffersActivity
import ru.autokit.android.screens.schedule.ScheduleActivity
import ru.autokit.android.screens.spares.SparesActivity
import ru.autokit.android.screens.submitform.SubmitActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [Module::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: IntroActivity)
    fun inject(activity: MileageActivity)
    fun inject(sparesActivity: SparesActivity)
    fun inject(scheduleActivity: ScheduleActivity)
    fun inject(submitActivity: SubmitActivity)
    fun inject(finalScreen: FinalScreen)
    fun inject(activity: CalendarActivity)
    fun inject(activity: OffersActivity)

}