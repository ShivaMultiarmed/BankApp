package mikhail.shell.bank.app.data.repository

import app.cash.turbine.test
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import mikhail.shell.bank.app.domain.repository.TransactionsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TransactionRepositoryWithFireStoreTests {
    @get:Rule
    val rule = HiltAndroidRule(this)
    @Inject
    lateinit var db: FirebaseFirestore
    lateinit var repository: TransactionsRepository
    @Before
    fun init() {
        rule.inject()
        repository = TransactionsRepositoryWithFireStore(db)
    }
    @Test
    fun testGettingTransactions() = runBlocking {
        val transactions = repository.getTransactionsByUserId("mDLOUJm8xZ48xVuhJf9Z")
        transactions.test {
            Assert.assertNotNull(awaitItem())
            awaitComplete()
        }
    }
}