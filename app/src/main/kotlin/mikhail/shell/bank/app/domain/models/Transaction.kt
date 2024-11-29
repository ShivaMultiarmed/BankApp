package mikhail.shell.bank.app.domain.models

import java.time.LocalDateTime

data class Transaction(
    val id: Long,
    val from: Long,
    val to: Long,
    val dateTime: LocalDateTime = LocalDateTime.now()
)