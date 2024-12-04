package mikhail.shell.bank.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mikhail.shell.bank.app.data.repository.AuthRepositoryWithFireAuthentication
import mikhail.shell.bank.app.data.repository.CardsRepositoryWithFireStore
import mikhail.shell.bank.app.data.repository.CurrenciesRepositoryImpl
import mikhail.shell.bank.app.data.repository.ProfileRepositoryWithFireStore
import mikhail.shell.bank.app.data.repository.ToolsRepositoryImpl
import mikhail.shell.bank.app.data.repository.TransactionsRepositoryWithFireStore
import mikhail.shell.bank.app.domain.repository.AuthRepository
import mikhail.shell.bank.app.domain.repository.CardsRepository
import mikhail.shell.bank.app.domain.repository.CurrenciesRepository
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import mikhail.shell.bank.app.domain.repository.ToolsRepository
import mikhail.shell.bank.app.domain.repository.TransactionsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindProfileRepository(repositoryImpl: ProfileRepositoryWithFireStore) : ProfileRepository
    @Binds
    @Singleton
    abstract fun bindCardsRepository(repositoryImpl: CardsRepositoryWithFireStore): CardsRepository
    @Binds
    @Singleton
    abstract fun bindServiceRepository(repositoryImpl: ToolsRepositoryImpl): ToolsRepository
    @Binds
    @Singleton
    abstract fun bindCurrenciesRepository(repositoryImpl: CurrenciesRepositoryImpl): CurrenciesRepository
    @Binds
    @Singleton
    abstract fun bindAuthRepository(repositoryImpl: AuthRepositoryWithFireAuthentication): AuthRepository
    @Binds
    @Singleton
    abstract fun bindTransactionRepository(repositoryImpl: TransactionsRepositoryWithFireStore): TransactionsRepository
}