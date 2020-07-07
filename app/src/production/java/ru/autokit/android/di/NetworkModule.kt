package ru.autokit.android.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.autokit.android.network.Api
import ru.autokit.android.network.DefaultInterceptor
import ru.autokit.android.network.MockInterceptor
import javax.inject.Named
import javax.inject.Singleton


const val NETWORK = "NETWORK"
const val UI = "UI"
const val MOCK_INTERCEPTOR = "MOCK_INTERCEPTOR"
const val DEFAULT_INTERCEPTOR = "DEFAULT_INTERCEPTOR"

@Module
open class NetworkModule(val url: String, val context: Context) {

    @Singleton
    @Named(NETWORK)
    @Provides
    open fun provideNetworkScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Singleton
    @Named(UI)
    @Provides
    open fun provideUIScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Singleton
    @Provides
    open fun provideOkHttpClient(@Named(MOCK_INTERCEPTOR) interceptor: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(logging)
            .build()

        return httpClient
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: OkHttpClient): Api {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(url)
            .build()

        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Named(MOCK_INTERCEPTOR)
    @Provides
    fun provideMockInterceptor(): Interceptor {
        return MockInterceptor(context)
    }

    @Singleton
    @Named(DEFAULT_INTERCEPTOR)
    @Provides
    fun provideDefaultInterceptor(): Interceptor {
        return DefaultInterceptor()
    }
}