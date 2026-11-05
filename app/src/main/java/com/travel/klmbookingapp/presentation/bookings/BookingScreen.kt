package com.travel.klmbookingapp.presentation.bookings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.travel.klmbookingapp.R
import com.travel.klmbookingapp.core.constants.AppConstants
import com.travel.klmbookingapp.presentation.components.ErrorView
import com.travel.klmbookingapp.presentation.components.LoadingView
import com.travel.klmbookingapp.ui.theme.Dimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BookingScreen(
    viewModel: BookingViewModel,
    snackBarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
    navigateToDetail: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { 2 }
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is BookingEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }

                is BookingEvent.NavigateToDetail -> {
                    navigateToDetail(event.bookingId)
                }
            }
        }
    }

    when {
        state.isLoading -> LoadingView()

        state.errorMessage != null -> {
            ErrorView(state.errorMessage ?: stringResource(R.string.error))
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.halfwhite))
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.spacing16),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.my_bookings),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Dimens.spacing10,
                            vertical = Dimens.spacing12
                        )
                        .clip(RoundedCornerShape(Dimens.spacing10))
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(Dimens.spacing4))
                            .background(
                                if (pagerState.currentPage == AppConstants.UPCOMING_BOOKINGS_TAB) Color.White
                                else Color.LightGray
                            )
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        AppConstants.UPCOMING_BOOKINGS_TAB
                                    )
                                }
                            }
                            .padding(vertical = Dimens.spacing12),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.upcoming),
                            color = if (pagerState.currentPage == AppConstants.UPCOMING_BOOKINGS_TAB)
                                colorResource(R.color.btn_blue)
                            else
                                Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(Dimens.spacing4))
                            .background(
                                if (pagerState.currentPage == AppConstants.PAST_BOOKINGS_TAB) Color.White
                                else Color.LightGray
                            )
                            .clickable {
                                scope.launch { pagerState.animateScrollToPage(AppConstants.PAST_BOOKINGS_TAB) }
                            }
                            .padding(vertical = Dimens.spacing12),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.past),
                            color = if (pagerState.currentPage == AppConstants.PAST_BOOKINGS_TAB) colorResource(
                                R.color.btn_blue
                            )
                            else Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        AppConstants.UPCOMING_BOOKINGS_TAB -> {
                            BookingPagerContent(
                                bookings = state.currentBookings,
                                onItemClick = { viewModel.onBookingClicked(it) }
                            )
                        }

                        AppConstants.PAST_BOOKINGS_TAB -> {
                            BookingPagerContent(
                                bookings = state.pastBookings,
                                onItemClick = { viewModel.onBookingClicked(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}