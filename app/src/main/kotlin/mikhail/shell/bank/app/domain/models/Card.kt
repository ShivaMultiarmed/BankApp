package mikhail.shell.bank.app.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val system: CardSystem,
    val type: CardType,
    val number: String,
    var balance: Double = 0.0
)
@Serializable
enum class CardSystem (val title: String) {
    VISA("Visa"), MASTERCARD("MasterCard")
}
@Serializable
enum class CardType (val purpose: String) {
    BUSINESS("Бизнес"), PENSION("Пенсия"), SAVINGS("Сбережения")
}