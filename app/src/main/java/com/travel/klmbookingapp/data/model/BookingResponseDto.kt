package com.travel.klmbookingapp.data.model

data class BookingResponseDto(
    val currentBookings: List<BookingDto>,
    val pastBookings: List<BookingDto>
)

data class BookingDto(
    val id: String,
    val imageUrl: String,
    val source: String,
    val destination: String,
    val departureDate: String,
    val arrivalDate: String,
    val bookingId: String,
    val passengerCount: Int,
    val flightNumber: String,
    val status: String,
    val tripDuration: String,
    val timeline: List<TimelineEventResponseDto>
)

data class TimelineEventResponseDto(
    val id: String,
    val type: String,
    val date: String?,
    val scheduledTime: String?,
    val actualTime: String?,
    val location: String?,
    val status: String?,
    val transferDuration: String?
)




