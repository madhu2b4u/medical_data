package com.demo.med.home.presentation.homescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.demo.med.database.entites.HealthData
import java.util.Calendar

@Composable
fun HomeFragment(
    viewModel: HomeViewModel,
    userName: String,
    onClickToDetailScreen: (data: HealthData) -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        TwoScreensVertically(userName, viewModel, onClickToDetailScreen)
    }

}

@Composable
fun TwoScreensVertically(
    userName: String?,
    viewModel: HomeViewModel,
    onClickToDetailScreen: (data: HealthData) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth(),
        ) {
            GreetingScreen(userName)
        }
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            HomeScreen(viewModel, onClickToDetailScreen)
        }
    }
}


@Composable
fun GreetingScreen(userName: String?) {
    val greetingMessage = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "Good morning"
        in 12..17 -> "Good afternoon"
        else -> "Good evening"
    }
    val fullGreeting = "$greetingMessage, $userName"

    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = fullGreeting,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickToDetailScreen: (data: HealthData) -> Unit = {},
) {

    val dataState: LiveData<MutableList<HealthData>> = viewModel.data

    val showLoaderState: LiveData<Boolean> = viewModel.showLoader

    val observedDataState: List<HealthData>? by dataState.observeAsState(null)

    val observedShowLoaderState: Boolean? by showLoaderState.observeAsState(false)

    val resultState by viewModel.healthData.observeAsState()

    if (observedShowLoaderState == false) {
        observedDataState?.let { healthData ->
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(
                    healthData
                ) {
                    CardItem(healthData = it, onClickToDetailScreen = {
                        onClickToDetailScreen.invoke(it)

                    })
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Red,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun CardItem(
    healthData: HealthData,
    onClickToDetailScreen: (data: HealthData) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .clickable {
                onClickToDetailScreen.invoke(healthData)
            }
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = healthData.problemName,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = healthData.medicationName.toString() + "-" + healthData.medicationStrength,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Dose: " + getDose(healthData),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

fun getDose(healthData: HealthData): String {
    return if (healthData.medicationDose?.isEmpty() == true) " Not Available"
    else healthData.medicationDose.toString()
}