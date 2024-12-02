package mikhail.shell.bank.app

import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import mikhail.shell.bank.app.data.repository.AuthRepositoryWithFireAuthentication
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
    lateinit var repository: AuthRepository
    @Before
    fun init () {
        hiltRule.inject()
        repository = AuthRepositoryWithFireAuthentication(auth)
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
    fun testSigningUp() {
        val latch = CountDownLatch(1)
        val email = "mikhail.shell@yandex.ru"
        val password = "abcdef"
        repository.signup(
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
}