package com.travel.klmbookingapp.domain.repository

import com.travel.klmbookingapp.domain.model.Booking
import com.travel.klmbookingapp.domain.model.Bookings

interface BookingRepository {

    suspend fun getBookings(): Bookings
    suspend fun getBookingDetail(bookingId: String): Booking

}