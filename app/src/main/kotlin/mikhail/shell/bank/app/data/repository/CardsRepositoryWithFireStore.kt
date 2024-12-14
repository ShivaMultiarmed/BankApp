package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.domain.errors.CardError
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.CardSystem
import mikhail.shell.bank.app.domain.models.CardType
import mikhail.shell.bank.app.domain.repository.CardsRepository
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

class CardsRepositoryWithFireStore @Inject constructor(
    private val db: FirebaseFirestore
) : CardsRepository {

    private val cards = db.collection("cards")

    override fun getCards(userid: String): Flow<List<Card>> {
        val flow = MutableStateFlow(listOf<Card>())
        cards.whereEqualTo("userid", userid)
            .addSnapshotListener { list, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                val cardsList = list?.map { snapshot -> snapshot.toCard() } ?: listOf()
                flow.value = cardsList
            }
        return flow
    }

    override fun createCard(
        card: Card,
        onSuccess: (Long) -> Unit,
        onFailure: (CardError) -> Unit
    ) {
        val number = createRandomNumber()
        cards.document(number.toString())
            .set(card.copy(number = number))
            .addOnSuccessListener {
                onSuccess(number)
            }.addOnFailureListener { exception ->
                val error = when (exception) {
                    else -> CardError.UNEXPECTED_ERROR
                }
                onFailure(error)
            }
    }

    override suspend fun getCard(cardNumber: Long): Card {
        return cards.document(cardNumber.toString()).get().await().toCard()
    }

    override fun updateCard(
        card: Card,
        onSuccess: (Card) -> Unit,
        onFailure: (CardError) -> Unit
    ) {
        cards.document(card.number.toString())
            .set(card)
            .addOnSuccessListener {
                onSuccess(card)
            }.addOnFailureListener { exception ->
                val error = when (exception) {
                    else -> CardError.UNEXPECTED_ERROR
                }
                onFailure(error)
            }
    }

    override fun deleteCard(
        cardNumber: Long,
        onSuccess: () -> Unit,
        onFailure: (CardError) -> Unit
    ) {
        cards.document(cardNumber.toString())
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                val error = when (exception) {
                    else -> CardError.UNEXPECTED_ERROR
                }
                onFailure(error)
            }
    }

    private fun createRandomNumber() = Random(Instant.now().toEpochMilli()).nextLong(
        1000_0000_0000_0000,
        9999_9999_9999_9999
    )

}

fun DocumentSnapshot.toCard(): Card {
    return data?.let { map ->
        Card(
            userid = map["userid"] as String,
            system = CardSystem.valueOf(map["system"] as String),
            type = CardType.valueOf(map["type"] as String),
            number = map["number"] as Long,
            balance = map["balance"] as Double
        )
    } ?: Card()
}