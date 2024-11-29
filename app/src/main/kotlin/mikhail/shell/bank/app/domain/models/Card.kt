package mikhail.shell.bank.app.domain.models

import kotlinx.serialization.Serializable
import java.time.Instant
import kotlin.random.Random

@Serializable
data class Card(
    val userid: String = "",
    val system: CardSystem = CardSystem.MASTERCARD,
    val type: CardType = CardType.SAVINGS,
    val number: Long = createRandomNumber(),
    var balance: Double = 0.0
) {
  companion object {
      fun createRandomNumber() = Random(Instant.now().toEpochMilli()).nextLong(
          1000_0000_0000_0000,
                9999_9999_9999_9999
          )
  }
}
@Serializable
enum class CardSystem (val title: String) {
    VISA("Visa"), MASTERCARD("MasterCard")
}
@Serializable
enum class CardType (val purpose: String) {
    BUSINESS("Бизнес"), PENSION("Пенсия"), SAVINGS("Сбережения")
}