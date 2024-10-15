package mikhail.shell.bank.app.ui.sections

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.CurrencyPound
import androidx.compose.material.icons.rounded.CurrencyYen
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mikhail.shell.bank.app.data.Currency


val currencies = listOf(
    Currency("USD", Icons.Rounded.CurrencyExchange, 90.0, 92.0),
    Currency("YEN", Icons.Rounded.CurrencyYen, 50.0, 54.0),
    Currency("GBP", Icons.Rounded.CurrencyPound, 105.0, 107.0),
)

@Preview
@Composable
fun CurrenciesSection()
{
    var isVisible by remember {
        mutableStateOf(false)
    }
    var openCloseVector by remember {
        mutableStateOf(Icons.Rounded.KeyboardArrowUp)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isVisible = if (isVisible) false else true
                        openCloseVector =
                            if (isVisible)
                                Icons.Rounded.KeyboardArrowDown
                            else
                                Icons.Rounded.KeyboardArrowUp
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ){
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    imageVector = openCloseVector,
                    contentDescription = "Курс валют",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(text = "Курс валют")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isVisible) 40.dp else 0.dp)
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            )
            {
                Text(text = "Валюта")
                Text(text = "Покупка")
                Text(text = "Продажа")
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isVisible) 220.dp else 0.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .animateContentSize()
            ) {
                items(currencies) { currency ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    )
                    {
                        Text(text = currency.name)
                        Text(text = "${currency.buy}")
                        Text(text = "${currency.sell}")
                    }
                }
            }
        }
    }

}