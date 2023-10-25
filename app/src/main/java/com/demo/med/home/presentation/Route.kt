package com.demo.med.home.presentation

import com.demo.med.common.DETAIL_SCREEN
import com.demo.med.common.HOME_SCREEN
import com.demo.med.common.MEDICAL_DETAIL

sealed class Route(val route: String) {
    object Home : Route(HOME_SCREEN)
    object Detail : Route("$DETAIL_SCREEN/{$MEDICAL_DETAIL}") {
        fun createRoute(data: String) = "$DETAIL_SCREEN/$data"
    }
}