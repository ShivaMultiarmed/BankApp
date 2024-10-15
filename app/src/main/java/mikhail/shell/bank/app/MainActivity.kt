package mikhail.shell.bank.app

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mikhail.shell.bank.app.ui.sections.CardsSection
import mikhail.shell.bank.app.ui.sections.CurrenciesSection
import mikhail.shell.bank.app.ui.sections.FinanceSection
import mikhail.shell.bank.app.ui.sections.WalletSection
import mikhail.shell.bank.app.ui.theme.BankAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankAppTheme {
                HomeScreen()
            }
        }
    }
}

@Preview
@Composable
fun HomeScreen()
{
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomNavigationBar()
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
        ) {
            WalletSection()
            CardsSection()
            FinanceSection()
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(0.dp)
                    .fillMaxWidth()
            )
            CurrenciesSection()
        }

    }
}


