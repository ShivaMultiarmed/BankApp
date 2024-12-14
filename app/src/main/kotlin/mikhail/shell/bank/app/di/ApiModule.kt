package mikhail.shell.bank.app.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mikhail.shell.bank.app.data.remote.CardsApi
import mikhail.shell.bank.app.data.remote.CurrenciesApi
import mikhail.shell.bank.app.data.remote.ProfileApi
import mikhail.shell.bank.app.data.remote.ServiceApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun providesGsonConverterFactory() = GsonConverterFactory.create(Gson())
    @Provides
    @Singleton
    fun provideHttpClient() = OkHttpClient()
    @Provides
    @Singleton
    @ApplicationRetrofit
    fun provideRetrofit(
        httpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl("https://google.com")
        .build()
    @Provides
    @Singleton
    fun provideCardsApi(
        @ApplicationRetrofit retrofit: Retrofit
    ) = retrofit.create<CardsApi>()
    @Provides
    @Singleton
    fun providesProfileApi(
        @ApplicationRetrofit retrofit: Retrofit
    ) = retrofit.create<ProfileApi>()
    @Provides
    @Singleton
    fun providesServicesApi(
        @ApplicationRetrofit retrofit: Retrofit
    ) = retrofit.create<ServiceApi>()
    @Provides
    @Singleton
    @CurrenciesRetrofit
    fun providesCurrenciesRetrofit() = Retrofit.Builder()
        .client(provideHttpClient())
        .baseUrl("https://v6.exchangerate-api.com/v6/")
        .addConverterFactory(providesGsonConverterFactory())
        .build()
    @Provides
    @Singleton
    fun providesCurrenciesApi(
        @CurrenciesRetrofit retrofit: Retrofit
    ) = retrofit.create<CurrenciesApi>()
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CurrenciesRetrofit

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationRetrofit