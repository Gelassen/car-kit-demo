package ru.rsprm.utils

import android.util.Log
import com.squareup.okhttp.HttpUrl
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource

import java.io.IOException

class ServerRule : ExternalResource() {

    private lateinit var server: MockWebServer

    private lateinit var dispatcher: AppServerDispatcher

    fun getUrl(): HttpUrl {
        return server.url("/test/")
    }

    fun getDispatcher(): AppServerDispatcher {
        return dispatcher
    }

    @Throws(Throwable::class)
    override fun before() {
        super.before()

        dispatcher = AppServerDispatcher()

        server = MockWebServer()
        server.start()
        server.setDispatcher(dispatcher)
    }

    override fun after() {
        super.after()
        try {
            server.shutdown()
        } catch (e: IOException) {
            Log.e("TAG", "Failed to shutdown server", e)
        }
    }
}
