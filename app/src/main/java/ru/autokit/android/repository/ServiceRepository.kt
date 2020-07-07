package ru.autokit.android.repository

import io.reactivex.Observable
import ru.autokit.android.model.*
import ru.autokit.android.network.Api

class ServiceRepository(var api: Api) {

    fun getVehicleByVIN(vin: String): Observable<VehicleData> {
        // TODO just a stub for API
//        return Observable.just(Vehicle())
        return api.getVINModel(vin)
    }

    fun pushCarMake(carMakeItem: CarMakeItem): Observable<CarMakeItem> {
        return api.postCarMake(carMakeItem)
    }

    fun pushCarModel(carModelItem: CarModelItem): Observable<CarModelItem> {
        return api.postModel(carModelItem)
    }

    fun getSpares(ids: String): Observable<CarSpares> {
        return api.getSpares(ids)
    }

    fun getMakes(): Observable<List<CarMakeItem>> {
        return api.getCarMakes().flatMap { it -> Observable.just(it.result) }
    }

    fun getModels(): Observable<List<CarModelItem>> {
        return api.getModel().flatMap { it -> Observable.just(it.result) }
    }

    fun getSchedule(time: Long): Observable<List<ScheduleItem>> {
        return api.getScheduleRequests(time)
            .flatMap { it -> Observable.just(it.result) }
    }

    fun pushSubmitForm(form: Form): Observable<Form> {
        return api.postForm(form)
    }

    fun getAvailableDates(): Observable<ScheduleDate> {
        return api.getAvailableDates()
    }

    fun getServices(mileage: String): Observable<ServiceData> {
        return api.getServices(mileage)
    }

}