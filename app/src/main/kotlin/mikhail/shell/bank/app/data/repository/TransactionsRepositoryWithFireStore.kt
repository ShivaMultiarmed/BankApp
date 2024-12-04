package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.data.exceptions.ReceiverNotFoundException
import mikhail.shell.bank.app.data.exceptions.SenderNotFoundException
import mikhail.shell.bank.app.data.exceptions.TooLargeAmountException
import mikhail.shell.bank.app.domain.models.Result
import mikhail.shell.bank.app.domain.models.Transaction
import mikhail.shell.bank.app.domain.models.TransactionError
import mikhail.shell.bank.app.domain.repository.TransactionsRepository
import java.time.LocalDateTime
import javax.inject.Inject

class TransactionsRepositoryWithFireStore @Inject constructor(
    private val db: FirebaseFirestore
) : TransactionsRepository {

    private val cards = db.collection("cards")
    private val transactions = db.collection("transactions")

    override fun getTransactionsByUserId(
        userid: String,
        from: LocalDateTime,
        to: LocalDateTime
    ) : Flow<List<Transaction>> {
        val flow = MutableStateFlow<List<Transaction>>(listOf())
        val cardsListener = cards.where(Filter.equalTo("userid", userid))
            .addSnapshotListener { cardsSnapshot, e ->
                if (e != null)
                    return@addSnapshotListener
                val fetchedCardNumbers = cardsSnapshot!!.documents.map {
                    it.toCard().number
                }
                transactions.where(
                    Filter.and(
                        Filter.or(
                            Filter.inArray("from", fetchedCardNumbers),
                            Filter.inArray("to", fetchedCardNumbers)
                        ),
                        //Filter.greaterThanOrEqualTo("dateTime", from),
                        //Filter.lessThanOrEqualTo("dateTime", to)
                    )
                ).orderBy("dateTime")
                    //.limit(100)
                    .addSnapshotListener { transactionsSnapshot, e ->
                    if (e != null)
                        return@addSnapshotListener
                    val transactionsList = transactionsSnapshot!!.documents.map {
                        it.toTransaction()
                    }
                    flow.value = transactionsList
                }
            }
        return flow
    }

    override suspend fun transferMoney(
        from: Long,
        to: Long,
        amount: Double
    ) : Result<Transaction, TransactionError> {
        val senderDocRef = cards.document(from.toString())
        val receiverDocRef = cards.document(to.toString())
        val newTransactionDocRef = transactions.document()
        try {
            val t = db.runTransaction { transaction ->
                val senderCardSnapshot = transaction.get(senderDocRef)
                if (!senderCardSnapshot.exists()) {
                    throw SenderNotFoundException()
                }
                val senderBalance = senderCardSnapshot.toCard().balance
                if (senderBalance < amount) {
                    throw TooLargeAmountException()
                }
                val receiverCardSnapshot = transaction.get(receiverDocRef)
                if (!receiverCardSnapshot.exists()) {
                    throw ReceiverNotFoundException()
                }
                val receiverBalance = receiverCardSnapshot.toCard().balance

                transaction.update(senderDocRef, "balance", senderBalance - amount)
                transaction.update(receiverDocRef, "balance", receiverBalance + amount)

                val newTransaction = Transaction(
                    id = newTransactionDocRef.id,
                    from = from,
                    to = to,
                    dateTime = LocalDateTime.now(),
                    amount = amount
                )
                transaction.set(newTransactionDocRef, newTransaction)
                newTransaction
            }.await()
            return Result.Success(t)
        } catch (e: TooLargeAmountException) {
            return Result.Failure(TransactionError.NOT_ENOUGH_MONEY)
        } catch (e: SenderNotFoundException) {
            return Result.Failure(TransactionError.SENDER_NOT_FOUND)
        } catch (e: ReceiverNotFoundException) {
            return Result.Failure(TransactionError.RECEIVER_NOT_FOUND)
        } catch (e: Exception) {
            return Result.Failure(TransactionError.NOT_ENOUGH_MONEY)
        }
    }
}

fun DocumentSnapshot.toTransaction() = data?.let {
    Transaction(
        id = it["id"] as String,
        from = (it["from"] as Double).toLong(),
        to = it["to"] as Long,
        amount = it["amount"] as Double
    )
} ?: Transaction()