package com.travel.klmbookingapp.presentation.navigation

import com.travel.klmbookingapp.core.constants.AppConstants

sealed class DestinationScreen(val route: String) {
    data object Bookings : DestinationScreen(route = AppConstants.BOOKINGS_ROUTE)
    data object Account : DestinationScreen(route = AppConstants.ACCOUNT_ROUTE)

    data object BookingDetail :
        DestinationScreen(route = "${AppConstants.BOOKING_DETAIL_ROUTE}/{${AppConstants.BOOKING_ID}}") {
        fun createRoute(bookingId: String) = "${AppConstants.BOOKING_DETAIL_ROUTE}/$bookingId"
    }
}
