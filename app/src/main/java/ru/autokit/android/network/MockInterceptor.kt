package ru.autokit.android.network

import android.content.Context
import android.util.Log
import okhttp3.*
import ru.autokit.android.BuildConfig

class MockInterceptor(val context: Context): Interceptor {

    companion object {

        private val URL_VEHICLE = "/api/VIN"

        private val URL_MAKES = "/api/Makes"

        private val URL_MODELS = "/api/Models"

        private val URL_SPARES = "/api/spares"

        private val URL_SCHEDULES = "/api/ScheduleRequests"

        private val URL_APPLICATION = "/api/application"

        private val URL_CALENDAR = "/api/calendar"

        private val URL_SERVICES = "/api/services"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!BuildConfig.DEBUG) throw IllegalAccessError("MockInterceptor should be used only with debug build.")

        var request = chain.request()
        val url = request.url().uri().toString()

        val msg = getMessage(getMockFileName(url, request.method()!!))
        val body = getBody(msg)

        return Response.Builder()
            .request(chain.request())
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(msg)
            .body(body)
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun getMessage(jsonName: String): String {
        return context.assets.open(jsonName).bufferedReader().use { it.readText() }
    }

    private fun getBody(msg: String): ResponseBody {
        return ResponseBody.create(
            MediaType.parse("application/json"),
            msg.toByteArray()
        )
    }

    private fun getMockFileName(url: String, method: String): String {
        lateinit var msg: String
        if (url.contains(URL_VEHICLE)) {
            msg = "mock_vehicle_response.json"
        } else if (url.contains(URL_MAKES) && method.equals("GET")) {
            msg = "mock_get_makes_response.json"
        } else if(url.contains(URL_MAKES) && method.equals("POST")) {
            msg = "mock_carmake_response.json"
        } else if (url.contains(URL_MODELS) && method.equals("GET")) {
            msg = "mock_get_models_response.json"
        } else if (url.contains(URL_MODELS) && method.equals("POST")) {
            msg = "mock_model_response.json"
        } else if (url.contains(URL_SPARES)) {
            msg = "mock_spares_full.json"
        } else if (url.contains(URL_SCHEDULES)) {
            msg = "mock_schedules_full.json"
        } else if (url.contains(URL_APPLICATION)) {
            msg = "mock_application.json"
        } else if (url.contains(URL_CALENDAR)) {
            msg = "mock_calendar.json"
        } else if (url.contains(URL_SERVICES)) {
            msg = "mock_services_full.json"
        }
        return msg
    }
}