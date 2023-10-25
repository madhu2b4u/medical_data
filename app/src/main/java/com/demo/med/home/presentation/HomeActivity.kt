package com.demo.med.home.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.med.common.MEDICAL_DETAIL
import com.demo.med.common.SpUtil
import com.demo.med.common.USERNAME
import com.demo.med.common.extensions.fromJson
import com.demo.med.common.extensions.toJson
import com.demo.med.database.entites.HealthData
import com.demo.med.home.presentation.detailscreen.DetailFragment
import com.demo.med.home.presentation.homescreen.HomeFragment
import com.demo.med.home.presentation.homescreen.HomeViewModel
import com.demo.med.ui.theme.MedicalDataTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    private val sharedPrefsHelpers = SpUtil.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userName = sharedPrefsHelpers?.getString(USERNAME)

        setContent {
            MedicalDataTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MedicalDataScreen(viewModel, userName.toString())
                }
            }
        }
    }
}

@Composable
fun MedicalDataScreen(viewModel: HomeViewModel, userName: String) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
    ) {
        composable(route = Route.Home.route) {
            HomeFragment(viewModel,
                userName,
                onClickToDetailScreen = {
                    val stringType = it.toJson().toString()
                    navController.navigate(
                        Route.Detail.createRoute(stringType)
                    )
                }
            )
        }

        composable(
            route = Route.Detail.route,
            arguments = listOf(
                navArgument(MEDICAL_DETAIL) { type = NavType.StringType })
        ) { backStackEntry ->
            val data = backStackEntry.arguments?.getString(MEDICAL_DETAIL)
            data?.fromJson(HealthData::class.java)?.let { DetailFragment(it, navController) }
        }
    }
}

