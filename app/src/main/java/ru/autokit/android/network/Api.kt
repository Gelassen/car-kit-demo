package ru.autokit.android.network

import io.reactivex.Observable
import retrofit2.http.*
import ru.autokit.android.model.*

interface Api {

    @GET("/api/VIN/{vin}")
    fun getVINModel(@Path("vin") vin: String): Observable<VehicleData>

    @POST("/api/Makes")
    fun postCarMake(@Body carMakeItem: CarMakeItem): Observable<CarMakeItem>

    @GET("/api/Makes")
    fun getCarMakes(): Observable<CarMake>

    @POST("/api/Models")
    fun postModel(@Body modelItem: CarModelItem): Observable<CarModelItem>

    @GET("/api/Models")
    fun getModel(): Observable<CarModel>

    @GET("/api/spares")
    fun getSpares(@Query("servicesIds") services: String): Observable<CarSpares>

    @GET("/api/ScheduleRequests/{forDate}")
    fun getScheduleRequests(@Path("forDate") time: Long): Observable<Schedule>

    @POST("/api/application")
    fun postForm(@Body form: Form): Observable<Form>

    @GET("/api/calendar")
    fun getAvailableDates(): Observable<ScheduleDate>

    @GET("/api/services/{mileage}")
    fun getServices(@Path("mileage") mileage: String): Observable<ServiceData>

}