package mikhail.shell.bank.app.domain.models

import java.time.LocalDateTime

data class Transaction(
    val id: String = "",
    val from: Long = 0,
    val to: Long = 0,
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val amount: Double = 0.0
)