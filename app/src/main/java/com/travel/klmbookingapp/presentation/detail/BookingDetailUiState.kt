package com.travel.klmbookingapp.presentation.detail

import com.travel.klmbookingapp.domain.model.Booking

data class BookingDetailUiState(
    val isLoading: Boolean = false,
    val booking: Booking? = null,
    val error: String? = null
)