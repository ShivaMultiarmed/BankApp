package mikhail.shell.bank.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mikhail.shell.bank.app.data.repository.CardsRepositoryImpl
import mikhail.shell.bank.app.data.repository.ProfileRepositoryImpl
import mikhail.shell.bank.app.domain.repository.CardsRepository
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnotherModule {
    @Binds
    @Singleton
    abstract fun bindProfileRepository(repositoryImpl: ProfileRepositoryImpl) : ProfileRepository
//    @Binds
//    @Singleton
//    abstract fun bindGetCardsUseCase(useCase: GetCards): GetCards
    @Binds
    @Singleton
    abstract fun bindCardsRepository(repositoryImpl: CardsRepositoryImpl): CardsRepository
}