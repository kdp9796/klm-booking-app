package com.travel.klmbookingapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.klmbookingapp.core.constants.AppConstants
import com.travel.klmbookingapp.domain.usecase.GetBookingDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingDetailViewModel @Inject constructor(
    private val getBookingDetailsUseCase: GetBookingDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val bookingId: String = checkNotNull(savedStateHandle[AppConstants.BOOKING_ID])
    private val _uiState = MutableStateFlow(BookingDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getBookingDetail()
    }

    private fun getBookingDetail() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val booking = getBookingDetailsUseCase(bookingId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        booking = booking
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}