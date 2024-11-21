package mikhail.shell.bank.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.CurrencyPound
import androidx.compose.material.icons.rounded.CurrencyYen
import androidx.compose.ui.graphics.Color
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mikhail.shell.bank.app.data.remote.CardsApi
import mikhail.shell.bank.app.data.remote.ProfileApi
import mikhail.shell.bank.app.data.remote.ServiceApi
import mikhail.shell.bank.app.di.RepositoriesModule
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.CardSystem
import mikhail.shell.bank.app.domain.models.CardType
import mikhail.shell.bank.app.domain.models.Currency
import mikhail.shell.bank.app.domain.models.FinanceTool
import mikhail.shell.bank.app.domain.repository.CardsRepository
import mikhail.shell.bank.app.domain.repository.CurrenciesRepository
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import mikhail.shell.bank.app.domain.repository.ToolsRepository
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [RepositoriesModule::class])
object TestRepositoriesModule {
    @Provides
    @Singleton
    fun providesTestCardsRepository(cardsApi: CardsApi) = object : CardsRepository {
        override suspend fun getCards(userid: Long): Flow<List<Card>> {
            return flow {
                emit(listOf(Card(CardSystem.MASTERCARD, CardType.BUSINESS, "2432 2342 7654 8776")))
            }
        }
    }
    @Provides
    @Singleton
    fun providesCurrenciesRepository(cardsApi: CardsApi) = object : CurrenciesRepository {
        val currencies = listOf(
            Currency("USD", Icons.Rounded.CurrencyExchange, 90.0, 92.0),
            Currency("YEN", Icons.Rounded.CurrencyYen, 50.0, 54.0),
            Currency("GBP", Icons.Rounded.CurrencyPound, 105.0, 107.0),
        )
        override suspend fun getCurrencies(): Flow<List<Currency>> = flow {
            delay(2000)
            emit(currencies)
        }
    }
    @Provides
    @Singleton
    fun providesServicesRepository(serviceApi: ServiceApi) = object : ToolsRepository {
        override suspend fun getRecommendedTools(userid: Long): Flow<List<FinanceTool>> {
            delay(1000)
            val toolsList = listOf(
                FinanceTool("Анализ расходов", Color(18, 99, 197, 255), Icons.Rounded.Analytics)
            )
            return flow {
                emit(toolsList)
            }
        }
    }
    @Provides
    @Singleton
    fun providesProfileRepository(profileApi: ProfileApi) = object : ProfileRepository {
        override suspend fun fetchProfile(userid: Long) : User {
            delay(3000)
            return User(404, "Cameron Magconahan", "qwerty", "Мужской")
        }
    }
}