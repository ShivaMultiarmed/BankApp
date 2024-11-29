package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.domain.models.Transaction
import mikhail.shell.bank.app.domain.repository.TransactionsRepository
import javax.inject.Inject

class TransactionsRepositoryWithFireStore @Inject constructor(
    private val db: FirebaseFirestore
): TransactionsRepository {

    private val cards = db.collection("cards")
    private val transactions = db.collection("transactions")

    override fun getTransactionsByUserId(userid: String) = flow {
        val cardNumbers = cards.whereEqualTo("userid", userid)
            .get().await().documents.map { snapshot -> snapshot.toCard().number }
        val transactionsList = transactions.where(Filter.or(
            Filter.inArray("from", cardNumbers),
            Filter.inArray("to", cardNumbers)
        )).get().await().documents.map {
            it.toTransaction()
        }
        emit(transactionsList)
    }
}

fun DocumentSnapshot.toTransaction() = data?.let {  Transaction(
    id = it["id"] as String,
    from = it["from"] as Long,
    to = it["to"] as Long,
    amount = it["amount"] as Double
)}?: Transaction()