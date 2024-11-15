package mikhail.shell.bank.app.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mikhail.shell.bank.app.data.remote.CardsApi
import mikhail.shell.bank.app.data.repository.CardsRepositoryImpl
import mikhail.shell.bank.app.domain.repository.CardsRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient() = OkHttpClient()
    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient) = Retrofit.Builder()
        .client(httpClient)
        .baseUrl("http://some.ru")
        .build()
    @Provides
    @Singleton
    fun provideCardsApi(retrofit: Retrofit) = retrofit.create(CardsApi::class.java)
    @Provides
    @Singleton
    fun providesCardsRepository(
        appContext: Application,
        cardsApi: CardsApi
    ) : CardsRepository = CardsRepositoryImpl(
        appContext,
        cardsApi
    )
}