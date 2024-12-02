package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

@HiltAndroidTest
class ProfileRepositoryWithFireStoreTests {
//    @get:Rule
//    val fireBaseRule = FirebaseTestRule()
    @get:Rule
    val rule = HiltAndroidRule(this)
    @Inject
    lateinit var firestore: FirebaseFirestore
    lateinit var repository: ProfileRepository
    @Before
    fun initialize()
    {
        rule.inject()
        repository = ProfileRepositoryWithFireStore(firestore)
    }
    @Test
    fun testAddingProfile() {
        val latch = CountDownLatch(1)
        var profile = User(
            name = "Miranda",
            password =  "abcde",
            gender = "Женщина"
        )
        repository.createProfile(
            profile,
            { id ->
                profile = profile.copy(userid = id)
                latch.countDown()
            },
            {
                latch.countDown()
            }
        )
        latch.await()
    }
}