package com.travel.klmbookingapp.data.repository

import com.travel.klmbookingapp.data.datasource.BookingLocalDataSource
import com.travel.klmbookingapp.data.mapper.MapperFactory
import com.travel.klmbookingapp.domain.model.Booking
import com.travel.klmbookingapp.domain.model.Bookings
import com.travel.klmbookingapp.domain.repository.BookingRepository
import javax.inject.Inject

class BookingRepositoryImplementation @Inject constructor(
    private val datasource: BookingLocalDataSource
) : BookingRepository {

    override suspend fun getBookings(): Bookings =
        MapperFactory.mapBookings(datasource.getBookings())

    override suspend fun getBookingDetail(bookingId: String): Booking {
        val bookingsDto = datasource.getBookings()
        val dto = (bookingsDto.currentBookings + bookingsDto.pastBookings)
            .firstOrNull { it.id == bookingId }
            ?: throw NoSuchElementException("Booking with id $bookingId not found")
        return MapperFactory.mapBooking(dto)
    }
}