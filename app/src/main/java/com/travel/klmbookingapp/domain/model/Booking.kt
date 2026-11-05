package com.travel.klmbookingapp.domain.model

data class Bookings(
    val upComingBookings: List<Booking>,
    val pastBookings: List<Booking>,
)

data class Booking(
    val id: String,
    val imageUrl: String,
    val source: String,
    val destination: String,
    val departureDate: String,
    val arrivalDate: String,
    val bookingId: String,
    val passengerCount: Int,
    val flightNumber: String,
    val status: BookingStatus,
    val tripDuration: String,
    val timeline: List<TimelineEvent>
)

enum class BookingStatus {
    UPCOMING,
    COMPLETED
}

data class TimelineEvent(
    val id: String,
    val type: TimelineType,
    val date: String,
    val scheduledTime: String,
    val actualTime: String,
    val location: String,
    val status: String,
    val transferDuration: String
)

enum class TimelineType {
    FLIGHT,
    TRANSFER
}