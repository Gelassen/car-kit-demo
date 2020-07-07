package ru.autokit.android.repository

import android.content.SharedPreferences
import io.reactivex.Observable
import ru.autokit.android.model.ScheduleItem

const val KEY_SPARES = "SPARES"
const val KEY_SCHEDULE = "SCHEDULE"
const val KEY_VIN = "VIN"
const val KEY_MILEAGE = "MILEAGE"

class CacheRepository(var pref: SharedPreferences) {

    fun saveSpares(json: String) {
        pref.edit()
            .putString(KEY_SPARES, json)
            .apply()
    }

    fun getSpares(): Observable<String> {
        return Observable.just(pref.getString(KEY_SPARES, "[]"))
    }

    fun saveSchedule(toJson: String) {
        pref.edit()
            .putString(KEY_SCHEDULE, toJson)
            .apply()

    }

    fun getCachedSchedule(): Observable<String> {
        return Observable.just(pref.getString(KEY_SCHEDULE, "{}"))
    }

    fun saveVin(vin: String) {
        pref.edit()
            .putString(KEY_VIN, vin)
            .apply()
    }

    fun getVin(): Observable<String> {
        return Observable.just(pref.getString(KEY_VIN, "{}"))
    }

    fun saveMileage(mileage: String) {
        pref.edit()
            .putString(KEY_MILEAGE, mileage)
            .apply()
    }

    fun getMileage(): Observable<String> {
        return Observable.just(pref.getString(KEY_MILEAGE, ""))
    }

    fun clear() {
        pref.edit()
            .clear()
            .apply()
    }
}