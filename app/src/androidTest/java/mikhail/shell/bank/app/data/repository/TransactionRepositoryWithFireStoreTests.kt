package mikhail.shell.bank.app.data.repository

import android.util.Log
import app.cash.turbine.test
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.domain.repository.TransactionsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import mikhail.shell.bank.app.domain.models.Result
import mikhail.shell.bank.app.domain.models.Transaction
import mikhail.shell.bank.app.domain.models.TransactionError

@HiltAndroidTest
class TransactionRepositoryWithFireStoreTests {
    private val TAG = "TransactionRepositoryWithFireStoreTests"

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject
    lateinit var db: FirebaseFirestore
    @Inject
    lateinit var auth: FirebaseAuth

    lateinit var repository: TransactionsRepository

    @Before
    fun init() = runBlocking {
        rule.inject()
        auth.signInWithEmailAndPassword("mikhail.shell@yandex.ru", "abcdef").await()
        repository = TransactionsRepositoryWithFireStore(db)
    }

    @Test
    fun testGettingTransactions() = runBlocking {
        val transactions = repository.getTransactionsByUserId(
            "mDLOUJm8xZ48xVuhJf9Z"
        )
        transactions.test {
            Assert.assertNotNull(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun testGettingTransactionsWithDateTimeFilter() = runBlocking {
        val latch = CountDownLatch(1)
        val userid = "fwla6Ug4EldSKPASk3b9m6kG9w23"
        val transactions = repository.getTransactionsByUserId(userid)
        transactions.collect {
            Log.i(TAG, it.size.toString())
            latch.countDown()
        }
        latch.await()
    }
    @Test
    fun testTransferringMoney_Success() = runBlocking {
        val sender = 1726_4075_5364_7490
        val reciever = 2503_9775_4155_2591
        val amount = 100.0
        val transactionResult = repository.transferMoney(sender, reciever, amount)
        Assert.assertTrue(transactionResult is Result.Success)
        val actualTransaction = (transactionResult as Result.Success).data
        val expectedTransaction = Transaction(
            id = actualTransaction.id,
            from = sender,
            to = reciever,
            amount = amount,
            dateTime = actualTransaction.dateTime
        )
        Assert.assertEquals(expectedTransaction, actualTransaction)
    }
    @Test
    fun testTransferringMoney_WrongSender() = runBlocking {
        val sender = 1726407553647491
        val reciever = 2503977541552591
        val amount = 100.0
        val transactionResult = repository.transferMoney(sender, reciever, amount)
        Assert.assertTrue(transactionResult == Result.Failure(TransactionError.SENDER_NOT_FOUND))
    }
    @Test
    fun testTransferringMoney_WrongReceiver() = runBlocking {
        val sender = 1726407553647490
        val reciever = 2503977541552590
        val amount = 100.0
        val transactionResult = repository.transferMoney(sender, reciever, amount)
        Assert.assertTrue(transactionResult == Result.Failure(TransactionError.RECEIVER_NOT_FOUND))
    }
    @Test
    fun testTransferringMoney_TooLargeAmount() = runBlocking {
        val sender = 1726407553647490
        val reciever = 2503977541552591
        val amount = 1_000_000.0
        val transactionResult = repository.transferMoney(sender, reciever, amount)
        Assert.assertTrue(transactionResult == Result.Failure(TransactionError.NOT_ENOUGH_MONEY))
    }
}