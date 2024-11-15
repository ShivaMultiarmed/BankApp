package mikhail.shell.bank.app.ui.sections.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import mikhail.shell.bank.app.R
import mikhail.shell.bank.app.data.Card
import mikhail.shell.bank.app.data.CardSystem.MASTERCARD
import mikhail.shell.bank.app.data.CardSystem.VISA
import mikhail.shell.bank.app.data.CardType.PENSION
import mikhail.shell.bank.app.data.CardType.SAVINGS
import mikhail.shell.bank.app.presentation.card.CardsViewModel

@Preview
@Composable
fun CardsSection(
    cardsViewModel: CardsViewModel = hiltViewModel<CardsViewModel>()
)
{
    var cardList: List<Card> = remember { mutableStateListOf() }
    var isLoaded by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        cardList = cardsViewModel.getCards()
        isLoaded = true
    }
    if (isLoaded)
    {
        LazyRow (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(cardList) { card ->
                CardComposable(card)
            }
        }
    }

}
//@Preview
@Composable
fun CardComposable(card: Card)
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
            Text(text = card.number, color = Color.White, fontSize = 16.sp)
        }
    }
}

fun gradient(start: Color, end: Color) = Brush.horizontalGradient(listOf(start, end))