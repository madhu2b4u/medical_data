package com.demo.med.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.med.common.extensions.shortToast
import com.demo.med.home.presentation.HomeActivity
import com.demo.med.ui.theme.MedicalDataTheme

class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicalDataTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(viewModel)
                    observeOnViewModel()
                }
            }
        }
    }

    private fun observeOnViewModel() = with(viewModel) {
        lifecycle.addObserver(this)

        loginSuccess.observe(this@LoginActivity) {
            if (it) {
                navigateToHomeScreen()
            } else {
                shortToast("Something went wrong")
            }
        }
    }

    private fun navigateToHomeScreen() {
        Intent(this@LoginActivity, HomeActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val usernameError = remember { mutableStateOf(false) }
        val passwordError = remember { mutableStateOf(false) }

        // Calculate whether the button should be enabled or not
        val isButtonEnabled = username.value.text.isNotEmpty() && password.value.text.isNotEmpty()

        Text(text = "Login", style = TextStyle(fontSize = 40.sp))

        Spacer(modifier = Modifier.height(20.dp))
        UserField(username, usernameError)
        PasswordField(password, passwordError)

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp)) {
            Button(
                onClick = {
                    if (isButtonEnabled) {
                        viewModel.onLogin(username.value.text, password.value.text)
                    }
                },
                enabled = isButtonEnabled, // Enable or disable the button based on the isButtonEnabled flag
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(text = "Login")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserField(username: MutableState<TextFieldValue>, usernameError: MutableState<Boolean>) {
    OutlinedTextField(
        value = username.value,
        onValueChange = {
            username.value = it
            usernameError.value = it.text.isEmpty()
        },
        label = {
            Text(text = "Username")
        },
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 8.dp)
    )

    if (usernameError.value) {
        Text(
            text = "Username cannot be empty",
            color = Color.Red,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password: MutableState<TextFieldValue>, passwordError: MutableState<Boolean>) {
    val revealPassword = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password.value,
        onValueChange = {
            password.value = it
            passwordError.value = it.text.isEmpty()
        },
        visualTransformation = if (revealPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if (revealPassword.value) {
                IconButton(
                    onClick = {
                        revealPassword.value = false
                    },
                ) {
                    Icon(imageVector = Icons.Filled.Visibility, contentDescription = null)
                }
            } else {
                IconButton(
                    onClick = {
                        revealPassword.value = true
                    },
                ) {
                    Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = null)
                }
            }
        },
        label = {
            Text(text = "Password")
        },
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 16.dp)
    )

    if (passwordError.value) {
        Text(
            text = "Password cannot be empty",
            color = Color.Red,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}