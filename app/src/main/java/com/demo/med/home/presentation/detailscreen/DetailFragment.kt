package com.demo.med.home.presentation.detailscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.demo.med.R
import com.demo.med.database.entites.HealthData
import com.demo.med.home.presentation.homescreen.getDose

@Composable
fun DetailFragment(data: HealthData, navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        DetailScreen(data, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    data: HealthData,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.medical_Details))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        content = {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = data.problemName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp, start = 24.dp, end = 24.dp)
            )
            Text(
                text = data.medicationName.toString() + "-" + data.medicationStrength,
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp, start = 24.dp, end = 24.dp)
            )
            Text(
                text = "Dose: " + getDose(data),
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp, start = 24.dp, end = 24.dp)
            )
        }
    }
}