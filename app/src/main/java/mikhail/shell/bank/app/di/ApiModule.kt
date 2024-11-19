package mikhail.shell.bank.app.di

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
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideHttpClient() = OkHttpClient()
    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl("https://google.com")
        .build()
    @Provides
    @Singleton
    fun provideCardsApi(retrofit: Retrofit)= retrofit.create<CardsApi>()
    @Provides
    @Singleton
    fun providesProfileApi(retrofit: Retrofit) = retrofit.create<ProfileApi>()
    @Provides
    @Singleton
    fun providesServicesApi(retrofit: Retrofit) = retrofit.create<ServiceApi>()
    @Provides
    @Singleton
    fun providesCurrenciesApi(retrofit: Retrofit) = retrofit.create<CurrenciesApi>()
}