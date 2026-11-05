package com.travel.klmbookingapp.data.datasource

import android.content.Context
import com.travel.klmbookingapp.data.model.BookingResponseDto
import com.google.gson.Gson
import com.travel.klmbookingapp.core.constants.AppConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class BookingLocalDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private val gson = Gson()
    suspend fun getBookings(): BookingResponseDto = withContext(Dispatchers.IO) {
        delay(AppConstants.API_DELAY_MS.milliseconds)
        val json = context.assets.open("bookings.json").bufferedReader()
            .use { it.readText() }
        gson.fromJson(json, BookingResponseDto::class.java)
    }
}