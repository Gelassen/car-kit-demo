package ru.autokit.android.providers

import android.telephony.PhoneNumberUtils
import android.util.Patterns
import ru.autokit.android.annotations.Mockable
import ru.autokit.android.model.CarMileage
import ru.autokit.android.model.Vehicle
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

const val VIN_PATTERN = "[a-zA-Z0-9]{17,17}+"

const val MILEAGE_PATTERN = "[0-9]{1,7}+"

const val YEAR_PATTERN = "[0-9]{1,4}+"

@Mockable
class ValidatorProvider {

    fun validate(entry: String): Boolean {
        return Pattern.compile(VIN_PATTERN).matcher(entry).matches()
    }

    fun validate(entry: Vehicle): Boolean {
        return !entry.isEmpty()
                && (entry.carYear == null || Pattern.compile(YEAR_PATTERN).matcher(entry.carYear).matches())
    }

    fun validate(carMileage: CarMileage): Boolean {
        return carMileage.entry.length > 0 && Pattern.compile(MILEAGE_PATTERN).matcher(carMileage.entry).matches()
    }

    fun validate(phone: String?, email: String?): Boolean {
        return PhoneNumberUtils.isGlobalPhoneNumber(phone) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validate(date: Long, availableTime: ArrayList<Long>): Boolean {
        val paramCalendar = Calendar.getInstance()
        val compareCalendar = Calendar.getInstance()

        paramCalendar.time = Date(date)
        for (availableDate in availableTime) {
            compareCalendar.time = Date(availableDate)
            if (paramCalendar.time.time >= Date().time
                && paramCalendar.get(Calendar.DAY_OF_YEAR) == compareCalendar.get(Calendar.DAY_OF_YEAR)
                && paramCalendar.get(Calendar.YEAR) == compareCalendar.get(Calendar.YEAR)) {
                return true
            }
        }

        return false
    }
}