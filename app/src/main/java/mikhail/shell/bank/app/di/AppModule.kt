package mikhail.shell.bank.app.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import mikhail.shell.bank.app.data.remote.CardsApi
import mikhail.shell.bank.app.data.remote.CurrenciesApi
import mikhail.shell.bank.app.data.remote.ProfileApi
import mikhail.shell.bank.app.data.remote.ServiceApi
import mikhail.shell.bank.app.data.repository.CardsRepositoryImpl
import mikhail.shell.bank.app.data.repository.ProfileRepositoryImpl
import mikhail.shell.bank.app.domain.repository.CardsRepository
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import mikhail.shell.bank.app.domain.usecases.GetCards
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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
    fun provideCardsApi(
        retrofit: Retrofit
    ): CardsApi = retrofit.create(CardsApi::class.java)
    @Provides
    @Singleton
    fun providesCardsRepository(
        appContext: Application,
        cardsApi: CardsApi
    ): CardsRepository = CardsRepositoryImpl(
        appContext,
        cardsApi
    )
    @Provides
    @Singleton
    fun providesProfileApi(retrofit: Retrofit) = retrofit.create(ProfileApi::class.java)
//    @Provides
//    @Singleton
//    fun providesProfileRepository(profileApi: ProfileApi) = ProfileRepositoryImpl(profileApi) as ProfileRepository
    // fun providesProfileRepository() = ProfileRepositoryImpl() as ProfileRepository
//    @Provides
//    @Singleton
//    fun providesGetCardsUseCase(cardsRepository: CardsRepository) = GetCards(cardsRepository)
//    fun providesGetCardsUseCase() = GetCards()
    @Provides
    @Singleton
    fun providesServicesApi(retrofit: Retrofit) = retrofit.create<ServiceApi>()
    @Provides
    @Singleton
    fun providesCurrenciesApi(retrofit: Retrofit) = retrofit.create<CurrenciesApi>()
}