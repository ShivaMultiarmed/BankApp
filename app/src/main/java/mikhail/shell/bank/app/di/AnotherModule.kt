package mikhail.shell.bank.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mikhail.shell.bank.app.data.repository.ProfileRepositoryImpl
import mikhail.shell.bank.app.data.repository.ServiceRepositoryImpl
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import mikhail.shell.bank.app.domain.repository.ServiceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnotherModule {
    @Binds
    @Singleton
    abstract fun bindProfileRepository(repositoryImpl: ProfileRepositoryImpl) : ProfileRepository
//    @Binds
//    @Singleton
//    abstract fun bindCardsRepository(repositoryImpl: CardsRepositoryImpl): CardsRepository
    @Binds
    @Singleton
    abstract fun bindServiceRepository(repositoryImpl: ServiceRepositoryImpl): ServiceRepository
}