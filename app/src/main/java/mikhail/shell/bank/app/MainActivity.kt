package mikhail.shell.bank.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mikhail.shell.bank.app.ui.HomeScreen
import mikhail.shell.bank.app.ui.ProfileScreen
import mikhail.shell.bank.app.ui.sections.home.CardsSection
import mikhail.shell.bank.app.ui.sections.home.CurrenciesSection
import mikhail.shell.bank.app.ui.sections.home.FinanceSection
import mikhail.shell.bank.app.ui.sections.home.WalletSection
import mikhail.shell.bank.app.ui.sections.profile.AvatarSection
import mikhail.shell.bank.app.ui.theme.BankAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankAppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        BottomNavigationBar()
                    }
                ) { innerPadding ->
                    //HomeScreen(innerPadding)
                    ProfileScreen(innerPadding)
                }

            }
        }
    }
}






