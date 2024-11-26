package mikhail.shell.bank.app.data.repository

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.CardSystem
import mikhail.shell.bank.app.domain.models.CardType
import mikhail.shell.bank.app.domain.repository.CardsRepository
import javax.inject.Inject

class CardsRepositoryWithFirebase @Inject constructor(
    private val db: FirebaseDatabase
) : CardsRepository {
    private val cards = db.reference.child("cards")
    override suspend fun getCards(userid: Long): Flow<List<Card>> {
        val flow = MutableStateFlow<List<Card>>(listOf())
        val snapshot = cards.orderByChild("userid").equalTo(userid.toDouble())

        snapshot.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val observedCards = snapshot.children.map { curSnapshot ->
                    val map = curSnapshot.value as Map<String, *>
                    Card(
                        map["userid"] as Long,
                        CardSystem.valueOf(map["system"] as String),
                        CardType.valueOf(map["type"] as String),
                        map["number"] as Long,
                        (map["balance"] as Long) * 1.0
                    )
                }
                flow.value = observedCards
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return flow
    }

    override suspend fun createCard(userid: Long, card: Card): Card {
        //val key = cards.push().key ?: throw Exception()
        cards.child(card.number.toString())
            .setValue(card)
            .await()
        return getCard(card.number)
    }

    override suspend fun getCard(cardNumber: Long): Card {
        return cards.child(cardNumber.toString())
            .get()
            .await()
            .getValue(Card::class.java) ?: throw Exception()
    }

    override suspend fun updateCard(card: Card): Card {
        cards.child(card.number.toString())
            .setValue(card)
            .await()
        return getCard(card.number)
    }

    override suspend fun deleteCard(cardNumber: Long) {
        cards.child(cardNumber.toString()).removeValue().await()
    }
}