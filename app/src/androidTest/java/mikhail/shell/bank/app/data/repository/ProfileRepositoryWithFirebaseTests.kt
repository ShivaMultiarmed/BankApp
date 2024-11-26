package mikhail.shell.bank.app.data.repository

import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ProfileRepositoryWithFirebaseTests {
    @get:Rule
    val rule = HiltAndroidRule(this)
    @Inject
    lateinit var firebaseDB: FirebaseDatabase
    lateinit var repository: ProfileRepository
    @Before
    fun initialize()
    {
        rule.inject()
        repository = ProfileRepositoryWithFirebase(firebaseDB)
    }
    @Test
    fun testAddingUser() = runBlocking {
        repository.createProfile(
            User(
                505,
                "Michael",
                "abcde",
                "Мужской"
            )
        )
    }
    @Test
    fun testGettingUser() = runTest {
        val user = repository.fetchProfile(505L)
        println(user)
    }
}