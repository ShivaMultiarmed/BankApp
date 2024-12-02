package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.domain.models.Transaction
import mikhail.shell.bank.app.domain.repository.TransactionsRepository
import javax.inject.Inject

class TransactionsRepositoryWithFireStore @Inject constructor(
    private val db: FirebaseFirestore
) : TransactionsRepository {

    private val cards = db.collection("cards")
    private val transactions = db.collection("transactions")

    override fun getTransactionsByUserId(
        userid: String
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
                    Filter.or(
                        Filter.inArray("from", fetchedCardNumbers),
                        Filter.inArray("to", fetchedCardNumbers)
                    )
                ).addSnapshotListener { transactionsSnapshot, e ->
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
}

fun DocumentSnapshot.toTransaction() = data?.let {
    Transaction(
        id = it["id"] as String,
        from = (it["from"] as Double).toLong(),
        to = it["to"] as Long,
        amount = it["amount"] as Double
    )
} ?: Transaction()