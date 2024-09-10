package com.jabama.challenge.auth.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jabama.challenge.auth.presentation.model.WebLoginResponse
import com.jabama.challenge.auth.presentation.viewmodel.AuthenticationEffects
import com.jabama.challenge.auth.presentation.viewmodel.AuthenticationEvents
import com.jabama.challenge.auth.presentation.viewmodel.AuthenticationViewModel


@Composable
fun AuthenticateScreen(
    modifier: Modifier = Modifier,
    webLoginResponse: WebLoginResponse,
    viewModel: AuthenticationViewModel
) {

    LaunchedEffect(key1 = viewModel.effects) {
        viewModel.effects.collect {
            //TODO handle this later
            when (it) {
                is AuthenticationEffects.ResultSuccess -> {
                    Log.d("AuthenticateScreen", "success")
                }

                is AuthenticationEffects.ResultFailure -> {
                    Log.d("AuthenticateScreen", "failed caused by ${it.message.toString()}")
                }
            }
        }
    }

    LaunchedEffect(key1 = webLoginResponse.code) {
        viewModel.invokeEvent(AuthenticationEvents.RefreshToken(webLoginResponse))
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(
            text = stringResource(R.string.login_in_progress),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAuthenticateScreen() {
    MaterialTheme {
        //TODO provide fake repository to create viewModel in preview and test functions
//        AuthenticateScreen(code = "", state = 0, viewModel = AuthenticationViewModel())
    }
}