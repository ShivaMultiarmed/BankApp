package mikhail.shell.bank.app.data.repository

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.Transaction
import mikhail.shell.bank.app.domain.repository.TransactionsRepository
import javax.inject.Inject

class TransactionsRepositoryWithFirebase @Inject constructor(
    private val db: FirebaseDatabase
): TransactionsRepository {

    private val cards = db.reference.child("cards")
    private val transactions = db.reference.child("transactions")

    override fun getTransactionsByUserId(userid: Long) = flow<Transaction> {
        val snapshot = cards.orderByChild("userid")
            .equalTo(userid.toDouble())
            .get()
            .await()
        val cardNumbers = snapshot.children.map {
            it.getValue(Card::class.java)?.number
        }
        transactions.orderByChild("from")
    }
}