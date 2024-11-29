package mikhail.shell.bank.app.di

import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FireServicesModule {
    @Provides
    @Singleton
    fun fireDatabase() = Firebase.database
    @Provides
    @Singleton
    fun FireStore() = Firebase.firestore
}