package ru.autokit.android.di

import android.content.Context
import dagger.Provides
import ru.autokit.android.converters.SparesItemConverter
import ru.autokit.android.network.Api
import ru.autokit.android.providers.OfferProvider
import ru.autokit.android.providers.ServiceProvider
import ru.autokit.android.providers.ValidatorProvider
import ru.autokit.android.repository.CacheRepository
import ru.autokit.android.repository.ServiceRepository
import javax.inject.Singleton

@dagger.Module(includes = [NetworkModule::class])
open class Module(val context: Context) {

    @Singleton
    @Provides
    open fun providesVinValidator(): ValidatorProvider {
        return ValidatorProvider()
    }

    @Singleton
    @Provides
    fun providesCacheRepository(): CacheRepository {
        return CacheRepository(context.getSharedPreferences("cache", Context.MODE_PRIVATE))
    }

    @Singleton
    @Provides
    fun providesServiceRepository(api: Api): ServiceRepository {
        return ServiceRepository(api)
    }

    @Singleton
    @Provides
    open fun providesServiceProvider(serviceRepository: ServiceRepository, cacheRepository: CacheRepository): ServiceProvider {
        return ServiceProvider(serviceRepository, cacheRepository)
    }

    @Singleton
    @Provides
    fun providesSparesItemConverter(): SparesItemConverter {
        return SparesItemConverter()
    }

    @Singleton
    @Provides
    fun providesOffersProvider(): OfferProvider {
        return OfferProvider()
    }
}