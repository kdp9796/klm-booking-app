package com.travel.klmbookingapp.data.mapper

import com.travel.klmbookingapp.core.constants.AppConstants
import com.travel.klmbookingapp.data.model.BookingDto
import com.travel.klmbookingapp.data.model.BookingResponseDto
import com.travel.klmbookingapp.data.model.TimelineEventResponseDto
import com.travel.klmbookingapp.domain.model.Booking
import com.travel.klmbookingapp.domain.model.BookingStatus
import com.travel.klmbookingapp.domain.model.Bookings
import com.travel.klmbookingapp.domain.model.TimelineEvent
import com.travel.klmbookingapp.domain.model.TimelineType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object MapperFactory {

    private val displayDateFormatter =
        DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT)

    fun mapBookings(dto: BookingResponseDto): Bookings {
        return Bookings(
            upComingBookings = dto.currentBookings.map { mapBooking(it) },
            pastBookings = dto.pastBookings.map { mapBooking(it) }
        )
    }

    fun mapBooking(dto: BookingDto): Booking {
        return Booking(
            id = dto.id,
            imageUrl = dto.imageUrl,
            source = dto.source,
            destination = dto.destination,
            departureDate = formatDate(dto.departureDate),
            arrivalDate = formatDate(dto.arrivalDate),
            bookingId = dto.bookingId,
            passengerCount = dto.passengerCount,
            flightNumber = dto.flightNumber,
            status = BookingStatus.valueOf(dto.status),
            tripDuration = dto.tripDuration,
            timeline = dto.timeline.map(::mapTimelineEvent)
        )
    }

    fun mapTimelineEvent(dto: TimelineEventResponseDto): TimelineEvent {
        return TimelineEvent(
            id = dto.id,
            type = TimelineType.valueOf(dto.type),
            date = dto.date.orEmpty(),
            scheduledTime = dto.scheduledTime.orEmpty(),
            actualTime = dto.actualTime.orEmpty(),
            location = dto.location.orEmpty(),
            status = dto.status.orEmpty(),
            transferDuration = dto.transferDuration.orEmpty()
        )
    }

    private fun formatDate(date: String): String {
        return try {
            LocalDate.parse(date).format(displayDateFormatter)
        } catch (e: DateTimeParseException) {
            date
        }
    }
}
