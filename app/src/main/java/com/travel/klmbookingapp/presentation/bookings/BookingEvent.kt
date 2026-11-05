package com.travel.klmbookingapp.presentation.bookings

sealed interface BookingEvent {
    data class ShowSnackBar(val message: String) : BookingEvent
    data class NavigateToDetail(val bookingId: String) : BookingEvent
}