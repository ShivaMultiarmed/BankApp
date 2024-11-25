package mikhail.shell.bank.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mikhail.shell.bank.app.data.repository.CardsRepositoryImpl
import mikhail.shell.bank.app.data.repository.CurrenciesRepositoryImpl
import mikhail.shell.bank.app.data.repository.ProfileRepositoryWithFirebase
import mikhail.shell.bank.app.data.repository.ToolsRepositoryImpl
import mikhail.shell.bank.app.domain.repository.CardsRepository
import mikhail.shell.bank.app.domain.repository.CurrenciesRepository
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import mikhail.shell.bank.app.domain.repository.ToolsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindProfileRepository(repositoryImpl: ProfileRepositoryWithFirebase) : ProfileRepository
    @Binds
    @Singleton
    abstract fun bindCardsRepository(repositoryImpl: CardsRepositoryImpl): CardsRepository
    @Binds
    @Singleton
    abstract fun bindServiceRepository(repositoryImpl: ToolsRepositoryImpl): ToolsRepository
    @Binds
    @Singleton
    abstract fun bindCurrenciesRepository(repositoryImpl: CurrenciesRepositoryImpl): CurrenciesRepository
}