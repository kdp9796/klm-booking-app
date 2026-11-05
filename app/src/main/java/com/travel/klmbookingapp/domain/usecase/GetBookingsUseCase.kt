package com.travel.klmbookingapp.domain.usecase

import com.travel.klmbookingapp.domain.model.Bookings
import com.travel.klmbookingapp.domain.repository.BookingRepository
import javax.inject.Inject

class GetBookingsUseCase @Inject constructor(private val bookingRepo: BookingRepository) {

    suspend operator fun invoke(): Bookings = bookingRepo.getBookings()

}