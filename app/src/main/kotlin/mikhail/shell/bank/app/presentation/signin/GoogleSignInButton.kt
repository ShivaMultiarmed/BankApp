package mikhail.shell.bank.app.presentation.signin


import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.R

@Composable
fun GoogleSignInButton(
    onSuccess: (Credential) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)
    Button(
        onClick = {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(true)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()
            coroutineScope.launch {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = context
                    )
                    onSuccess(result.credential)
                } catch (e: GetCredentialException) {
                    Toast.makeText(
                        context,
                        "Ошибка аутентификации: ${e.type}",
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.e("Sign in with Google", e.stackTraceToString())
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Ошибка аутентификации: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    ) {
        Text(
            text = "Войти через Google"
        )
    }
}