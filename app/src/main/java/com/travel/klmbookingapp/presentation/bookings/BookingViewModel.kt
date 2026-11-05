package com.travel.klmbookingapp.presentation.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.klmbookingapp.core.constants.AppConstants
import com.travel.klmbookingapp.domain.usecase.GetBookingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookingsUseCase: GetBookingsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<BookingEvent>()
    val event: SharedFlow<BookingEvent> = _event.asSharedFlow()

    init {
        loadBookings()
    }

    private fun loadBookings() {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }
                val bookings = bookingsUseCase()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        currentBookings = bookings.upComingBookings,
                        pastBookings = bookings.pastBookings
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun onFabClicked() {
        viewModelScope.launch {
            _event.emit(BookingEvent.ShowSnackBar(AppConstants.ACTION_CLICK))
        }
    }

    fun onBookingClicked(bookingId: String) {
        viewModelScope.launch {
            _event.emit(BookingEvent.NavigateToDetail(bookingId))
        }
    }
}