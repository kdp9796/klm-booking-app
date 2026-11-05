package com.travel.klmbookingapp.presentation.bookings

import com.travel.klmbookingapp.domain.model.Booking

data class BookingUiState(
    val isLoading: Boolean = false,
    val currentBookings: List<Booking> = emptyList(),
    val pastBookings: List<Booking> = emptyList(),
    val errorMessage: String? = null
)