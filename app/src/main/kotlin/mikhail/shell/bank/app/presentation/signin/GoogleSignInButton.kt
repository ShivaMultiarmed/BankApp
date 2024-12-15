package mikhail.shell.bank.app.presentation.signin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.R
import mikhail.shell.bank.app.sharedpreferences.getUserId

@Composable
fun GoogleSignInButton(
    onSuccess: (Credential) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                coroutineScope.launch {
                    val info = task.await()
                    val credential = GoogleIdTokenCredential(
                        info.id.orEmpty(),
                        info.idToken.orEmpty(),
                        info.displayName,
                        info.familyName,
                        info.givenName,
                        info.photoUrl,
                        null
                    )
                    onSuccess(credential)
                }
            } catch (e: Exception) {

            }
        }
    }

    Button(
        onClick = {
            if (GoogleSignIn.getLastSignedInAccount(context) != null) {
                oneTapSignIn(
                    context,
                    coroutineScope,
                    onSuccess,
                    {
                        defaultGoogleSignIn(context, launcher)
                    }
                )
            } else {
                defaultGoogleSignIn(context, launcher)
            }
        }
    ) {
        Text(
            text = "Войти через Google"
        )
    }

}
fun oneTapSignIn(
    context: Context,
    coroutineScope: CoroutineScope,
    onSuccess: (Credential) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val credentialManager = CredentialManager.create(context)
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
        } catch (e: GetCredentialCancellationException) {

        } catch (e: GetCredentialException) {
            onFailure(e)
            Log.e("Sign in with Google", e.stackTraceToString())
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}

fun defaultGoogleSignIn(
    context: Context,
    resultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val client = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    )
    val intent = client.signInIntent
    resultLauncher.launch(intent)
}