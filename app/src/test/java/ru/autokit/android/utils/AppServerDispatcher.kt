package ru.rsprm.utils


import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.RecordedRequest

import java.io.File

class AppServerDispatcher : Dispatcher() {

    var vehicleMockResponse: MockResponse? = null

    var makesResponse: MockResponse? = null

    var modelResponse: MockResponse? = null

    var sparesMockResponse: MockResponse? = null

    var serviceMockResponse: MockResponse? = null

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        var mockResponse = MockResponse().setResponseCode(200)
        val path = request.path
        if (path.contains(URL_VEHICLE)) {
            mockResponse = getVehicleResponse()
        } else if(path.contains(URL_MAKES) && request.method.equals("POST")) {
            mockResponse = getMakeResponse()
        } else if(path.contains(URL_MAKES) && request.method.equals("GET")) {
            setListMakesResponse()
            mockResponse = getMakeResponse()
        } else if (path.contains(URL_MODELS) && request.method.equals("POST")) {
            mockResponse = getModelAPIResponse()
        } else if (path.contains(URL_MODELS) && request.method.equals("GET")) {
            setListModelResponse()
            mockResponse = getModelAPIResponse()
        } else if (path.contains(URL_SPARES)) {
            mockResponse = getSparesAPIResponse()
        } else if (path.contains(URL_SERVICES)) {
            mockResponse = getServicesApiResponse()
        }
        return mockResponse
    }

    // TODO move to separate class wrappers when maintain this become painful
    // test, debug only

    fun setOkVehicleResponse() {
        vehicleMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_vehicle_response.json"))
    }

    fun setNotFoundVehicleResponse() {
        vehicleMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_not_found_vehicle_response.json"))
    }

    fun setOkMakesResponse() {
        makesResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_carmake_response.json"))
    }

    fun setListMakesResponse() {
        makesResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_get_makes_response.json"))
    }

    fun setOkModelResponse() {
        modelResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_model_response.json"))
    }

    fun setListModelResponse() {
        modelResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_get_models_response.json"))
    }

    fun setOkSparesResponse() {
        sparesMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_spares_full.json"))
    }

    fun setOkEmptySparesResponse() {
        sparesMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_spares_empty.json"))
    }

    fun setServerIssueSparesResponse() {
        sparesMockResponse = MockResponse()
            .setResponseCode(502)
    }

    fun setOkServicesResponse() {
        serviceMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_services_full.json"))
    }

    fun setOkEmptyServicesResponse() {
        serviceMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("mock_services_empty.json"))
    }

    fun getVehicleResponse(): MockResponse {
        if (vehicleMockResponse == null) {
            setOkVehicleResponse()
        }
        return vehicleMockResponse!!
    }

    fun getMakeResponse(): MockResponse {
        if (makesResponse == null) {
            setOkMakesResponse()
        }
        return makesResponse!!
    }

    fun getModelAPIResponse(): MockResponse {
        if (modelResponse == null) {
            setOkModelResponse()
        }
        return modelResponse!!
    }

    fun getSparesAPIResponse(): MockResponse {
        if (sparesMockResponse == null) {
            setOkSparesResponse()
        }
        return sparesMockResponse!!
    }

    fun getServicesApiResponse(): MockResponse {
        if (serviceMockResponse == null) {
            setOkServicesResponse()
        }
        return serviceMockResponse!!
    }

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    companion object {

        private val URL_VEHICLE = "/api/VIN"

        private val URL_MAKES = "/api/Makes"

        private val URL_MODELS = "/api/Models"

        private val URL_SPARES = "/api/spares"

        private val URL_SERVICES = "/api/services"
    }
}
