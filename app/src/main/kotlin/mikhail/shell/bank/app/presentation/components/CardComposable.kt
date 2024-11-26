package mikhail.shell.bank.app.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mikhail.shell.bank.app.R
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.CardSystem.MASTERCARD
import mikhail.shell.bank.app.domain.models.CardSystem.VISA

//@Preview
@Composable
fun CardComposable(
    card: Card,
    onClick: () -> Unit = {}
)
{
    val image = painterResource(
        when (card.system)
        {
            VISA -> R.drawable.ic_visa
            MASTERCARD -> R.drawable.ic_mastercard
        }
    )
    Box(
        modifier = Modifier
            .padding(end = 16.dp)
            .width(240.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                gradient(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.inversePrimary
                )
            )
            .padding(10.dp)
            .clickable {
                onClick()
            },
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start,
        ) {
            Row (
                modifier = Modifier
                    .wrapContentSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .width(30.dp)
                        .height(30.dp),
                    painter = image,
                    contentDescription = "Тип карты"
                )
                Text(text = card.type.purpose, color = Color.White, fontSize = 12.sp)
            }
            Text(text = "₽ ${card.balance}", color = Color.White, fontSize = 18.sp)
            Text(text = card.number.toString(), color = Color.White, fontSize = 16.sp)
        }
    }
}

fun gradient(start: Color, end: Color) = Brush.horizontalGradient(listOf(start, end))