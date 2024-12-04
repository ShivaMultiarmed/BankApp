package mikhail.shell.bank.app.presentation.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mikhail.shell.bank.app.domain.models.TransactionError

@Composable
fun AddTransactionScreen(
    navController: NavController = rememberNavController(),
    state: AddTransactionState = AddTransactionState(),
    onSubmit: (Long, Long, Double) -> Unit
) {
    Column {
        Spacer(
            modifier = Modifier.height(100.dp)
        )
        var from by rememberSaveable {
            mutableStateOf("1726407553647490")
        }
        var to by rememberSaveable {
            mutableStateOf("8501765996012836")
        }
        var amount by rememberSaveable {
            mutableStateOf("100.0")
        }
        TextField(
            value = from,
            onValueChange = {
                from = it
            }
        )
        TextField(
            value = to,
            onValueChange = {
                to = it
            }
        )
        TextField(
            value = amount,
            onValueChange = {
                amount = it
            }
        )
        Button(
            onClick = {
                val sender = try {
                    from.toLong()
                } catch (e: NumberFormatException) {
                    0
                }
                val receiver = try {
                    to.toLong()
                } catch (e: NumberFormatException) {
                    0
                }
                val money = try {
                    amount.toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                }
                onSubmit(sender, receiver, money)
            }
        ) {
            Text("Отправить")
        }
        if (state.transaction != null) {
            Text("Вы успешно отправили ${state.transaction.amount} на карту ${state.transaction.to} с карты ${state.transaction.from}.")
        } else if (state.error != null) {
            Text(
                text = when (state.error) {
                    TransactionError.NOT_ENOUGH_MONEY -> "Не достаточно средств"
                    TransactionError.SENDER_NOT_FOUND -> "Отправитель не найден"
                    TransactionError.RECEIVER_NOT_FOUND -> "Получатель не найден"
                    TransactionError.SENDER_NOT_SPECIFIED -> "Не указан отправитель"
                    TransactionError.RECEIVER_NOT_SPECIFIED -> "Не указан получатель"
                    TransactionError.AMOUNT_NOT_SPECIFIED -> "Не указана сумма"
                    TransactionError.AMOUNT_LESS_THAT_ZERO -> "Сумма должна быть больше нуля."
                }
            )
        }
    }
}