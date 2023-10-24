package com.demo.med

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demo.med.auth.LoginActivity
import com.demo.med.home.presentation.HomeActivity
import com.demo.med.ui.theme.MedicalDataTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicalDataTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SplashScreen(onTimeout = { viewModel.onTimeout() })
                    observeOnViewModel()
                }
            }
        }
    }

    private fun observeOnViewModel() = with(viewModel) {
        lifecycle.addObserver(this)

        showLogin.observe(this@MainActivity) {
            navigateToHomeOrLoginScreen(it)
        }
    }

    private fun navigateToHomeOrLoginScreen(it: Boolean) {
        when (it) {
            true -> navigateToHomeScreen()
            else -> navigateToLoginScreen()
        }
    }

    private fun navigateToHomeScreen() {
        Intent(this@MainActivity, HomeActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun navigateToLoginScreen() {
        Intent(this@MainActivity, LoginActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    LaunchedEffect(true) {
        delay(2000)
        onTimeout()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Splash Screen", style = MaterialTheme.typography.headlineLarge)
    }
}




