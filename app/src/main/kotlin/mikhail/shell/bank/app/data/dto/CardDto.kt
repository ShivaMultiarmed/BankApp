package mikhail.shell.bank.app.data.dto

import kotlinx.serialization.Serializable
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.CardSystem
import mikhail.shell.bank.app.domain.models.CardType

@Serializable
data class CardDto(
    val userid: String = "",
    val number: Long = 0,
    val system: CardSystem = CardSystem.MASTERCARD,
    val type: CardType = CardType.SAVINGS,
    var balance: Double = 0.0
)

fun Card.toDto(userid: String) = CardDto(
    userid = userid,
    system = system,
    type = type,
    balance = balance,
    number = number
)
fun CardDto.toModel() = Card (
    system = system,
    type = type,
    balance = balance,
    number = number
)