package mikhail.shell.bank.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.domain.repository.AuthRepository
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

@HiltAndroidTest
class AuthRepositoryWithFireAuthenticationTests {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
    @Inject
    lateinit var auth: FirebaseAuth
    @Inject
    lateinit var db: FirebaseFirestore
    lateinit var repository: AuthRepository
    @Before
    fun init () {
        hiltRule.inject()
        repository = AuthRepositoryWithFireAuthentication(auth, db)
    }
    @Test
    fun testSigningIn() {
        val latch = CountDownLatch(1)
        val email = "theoutstandingme@gmail.com"
        val password = "qwerty"
//        val email = "mikhail.shell@yandex.ru"
//        val password = "abcdef"
        repository.signin(
            email,
            password,
            {
                assertNotNull(it)
                latch.countDown()
            },
            {
                latch.countDown()
            }
        )
        latch.await()
    }
    @Test
    fun testSigningUp_Success() {
        val latch = CountDownLatch(1)
        val email = "henry@gmail.com"
        val password = "qwerty"
        val user = User(
            name = "Henry",
            gender = "Мужчина"
        )
        repository.signup(
            email,
            password,
            user,
            {
                assertNotNull(it)
                latch.countDown()
            },
            {
                latch.countDown()
            }
        )
        latch.await()
    }
    @Test
    fun testSignIn_InvalidCredentials() {
        val latch = CountDownLatch(1)
        val email = "henry@gmail.com"
        val password = "incorrectpassword"
        repository.signin(
            email,
            password,
            {
                assertNotNull(it)
                latch.countDown()
            },
            {
                assertNotNull(it)
                latch.countDown()
            }
        )
        latch.await()
    }
}