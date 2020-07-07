package ru.autokit.android.providers

import io.reactivex.Observable
import ru.autokit.android.converters.ScheduleItemConverter
import ru.autokit.android.converters.SparesItemConverter
import ru.autokit.android.model.*
import ru.autokit.android.repository.CacheRepository
import ru.autokit.android.repository.ServiceRepository
import javax.inject.Inject

open class ServiceProvider @Inject constructor(
    var repository: ServiceRepository,
    var cacheRepository: CacheRepository
) {

    fun getVehicleByVIN(vin: String): Observable<Vehicle> {
        return repository.getVehicleByVIN(vin)
            .flatMap { it -> Observable.just(it.result)}
    }

    @Deprecated(message = "In case server returns identical responses it would be nice to link requests")
    fun pushVehicle(vehicle: Vehicle): Observable<Any> {
//        pushCarMake(vehicle.makeItem).flatMap(Function())
        return Observable.concat(pushCarMake(vehicle.makeItem!!), pushModel(vehicle.modelItem!!))
    }

    fun pushCarMake(carMakeItem: CarMakeItem): Observable<CarMakeItem> {
        return if (carMakeItem.isEmpty()) Observable.just(CarMakeItem(null)) else repository.pushCarMake(carMakeItem)
    }

    fun pushModel(modelItem: CarModelItem): Observable<CarModelItem> {
        return if (modelItem.isEmpty()) Observable.just(CarModelItem(null)) else repository.pushCarModel(modelItem)
    }

    fun getSpares(offers: List<ServiceDataItem>): Observable<CarSpares> {
        val builder = StringBuilder()
        for (item in offers) {
            builder.append(item.id)
            builder.append(",")
        }
        return repository.getSpares(builder.toString())
    }

    fun cacheSparesItems(model: List<CarSparesItem>) {
        cacheRepository.saveSpares(SparesItemConverter().toJson(model))
    }

    fun getCachedSpares(): Observable<List<CarSparesItem>> {
        return cacheRepository
            .getSpares()
            .flatMap { it ->
                val cachedData = SparesItemConverter().fromJson(it)
                val result = ArrayList<CarSparesItem>()
                for (item in cachedData) {
                    result.add(item)
                }
                Observable.just(result)
            }
    }

    fun cacheScheduleItem(item: ScheduleItem) {
        cacheRepository.saveSchedule(ScheduleItemConverter().toJson(item))
    }

    fun getCachedSchedule(): Observable<ScheduleItem> {
        return cacheRepository
            .getCachedSchedule()
            .flatMap { it ->
                val cachedData = ScheduleItemConverter().fromJson(it)
                Observable.just(cachedData)
            }
    }

    fun getListOfMakes(): Observable<List<String>> {
        return repository
            .getMakes()
            .flatMap { it ->
                val result = ArrayList<String>()
                for (item in it) {
                    result.add(item.carMake!!)
                }
                Observable.just(result)
        }
    }

    fun getListOfModels(): Observable<List<String>> {
        return repository
            .getModels()
            .flatMap { it ->
                val result = ArrayList<String>()
                for (item in it) {
                    result.add(item.carModel!!)
                }
                Observable.just(result)
            }
    }

    fun getSchedule(date: Long): Observable<List<ScheduleItem>> {
        return repository.getSchedule(date)
    }

    fun pushSubmitForm(model: Form): Observable<Form> {
        return repository.pushSubmitForm(model)
    }

    fun clean() {
        cacheRepository.clear()
    }

    fun getAvailableDates(): Observable<ScheduleDate> {
        return repository.getAvailableDates()
    }

    fun getServices(mileage: String): Observable<ServiceData> {
        return repository.getServices(mileage)
    }
}