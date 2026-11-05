package com.travel.klmbookingapp.domain.usecase

import com.travel.klmbookingapp.domain.model.Booking
import com.travel.klmbookingapp.domain.repository.BookingRepository
import javax.inject.Inject

class GetBookingDetailsUseCase @Inject constructor(
    private val bookingRepo: BookingRepository
) {

    suspend operator fun invoke(bookingId: String): Booking =
        bookingRepo.getBookingDetail(bookingId)

}