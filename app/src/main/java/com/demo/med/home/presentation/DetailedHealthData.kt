package com.demo.med.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.med.database.entites.HealthData

@Composable
fun DetailedHealthData(healthData: HealthData, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display detailed information from the health data
        Text(
            text = "Problem Name: ${healthData.problemName}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = "Medication: ${healthData.medicationName} - ${healthData.medicationStrength}",
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = "Dose: ${getDose(healthData)}",
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
        Button(
            onClick = {
                onBackClick()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Back")
        }
    }
}
