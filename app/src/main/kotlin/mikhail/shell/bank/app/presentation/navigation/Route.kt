package mikhail.shell.bank.app.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object AuthGraph : Route() {
        @Serializable
        data object SignInRoute : Route()
        @Serializable
        data object SignUpRoute : Route()
    }
    @Serializable
    data object HomeScreenRoute : Route()
    @Serializable
    data object ProfileGraphRoute : Route() {
        @Serializable
        data class ProfileScreenRoute(val userid: String) : Route()
        @Serializable
        data object SettingsGraphRoute : Route() {
            @Serializable
            data object SettingsScreenRoute : Route()
            @Serializable
            data object AdvancedSettingsRoute : Route()
        }
    }
    @Serializable
    data object TransactionsGraph : Route() {
        @Serializable
        data object TransactionListRoute : Route()
        @Serializable
        data object AddTransactionRoute : Route()
    }
    @Serializable
    data object WalletScreenRoute : Route()
}