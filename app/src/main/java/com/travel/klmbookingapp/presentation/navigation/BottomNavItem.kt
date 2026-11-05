package com.travel.klmbookingapp.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.ui.graphics.vector.ImageVector
import com.travel.klmbookingapp.R

sealed class BottomNavItem(
    val route: String,
    @StringRes val titleRes: Int,
    val image: ImageVector
) {
    data object Bookings : BottomNavItem(
        route = DestinationScreen.Bookings.route,
        titleRes = R.string.bookings,
        image = Icons.Default.CalendarMonth
    )

    data object Account : BottomNavItem(
        route = DestinationScreen.Account.route,
        titleRes = R.string.account,
        image = Icons.Default.AccountCircle
    )
}