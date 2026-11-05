package com.travel.klmbookingapp.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.travel.klmbookingapp.R
import com.travel.klmbookingapp.core.constants.AppConstants
import com.travel.klmbookingapp.domain.model.Booking
import com.travel.klmbookingapp.domain.model.TimelineEvent
import com.travel.klmbookingapp.domain.model.TimelineType
import com.travel.klmbookingapp.presentation.components.ErrorView
import com.travel.klmbookingapp.presentation.components.LoadingView
import com.travel.klmbookingapp.ui.theme.Dimens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookingDetailScreen(
    onBackClick: () -> Unit,
    viewModel: BookingDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        state.isLoading -> LoadingView()

        state.error != null -> {
            ErrorView(state.error ?: stringResource(R.string.error))
        }

        state.booking != null -> {
            val booking = state.booking ?: return
            val listState = rememberLazyListState()

            val showToolbar by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 200
                }
            }

            Scaffold(
                containerColor = colorResource(R.color.halfwhite)
            ) { padding ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = padding
                    ) {
                        item {
                            HeaderSection(
                                booking = booking,
                                onBackClick = onBackClick,
                                listState = listState
                            )
                        }
                        item {
                            BookingInfoCard(booking = booking)
                        }
                        item {
                            TimelineSection(booking = booking)
                        }
                    }

                    AnimatedVisibility(
                        visible = showToolbar,
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        CenterAlignedTopAppBar(
                            title = { Text(text = booking.destination) },
                            navigationIcon = {
                                IconButton(onClick = onBackClick) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBackIosNew,
                                        contentDescription = null
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookingInfoCard(booking: Booking) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.spacing16)
    ) {
        Column(modifier = Modifier.padding(Dimens.spacing16)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.booking_reference),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Surface(
                    shape = RoundedCornerShape(AppConstants.ROUNDED_CORNER_SHAPE),
                    color = colorResource(R.color.btn_blue)
                ) {
                    Text(
                        text = booking.flightNumber,
                        color = Color.White,
                        modifier = Modifier.padding(
                            horizontal = Dimens.spacing18,
                            vertical = Dimens.spacing8
                        ),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(Dimens.spacing16))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.departure),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.width(Dimens.spacing4))

                Text(text = booking.departureDate)

                Spacer(Modifier.width(Dimens.spacing12))

                Icon(
                    imageVector = Icons.Default.FlightTakeoff,
                    contentDescription = null
                )

                Spacer(Modifier.width(Dimens.spacing4))

                Text(text = stringResource(R.string.round_trip))
            }
        }
    }
}

@Composable
private fun HeaderSection(
    booking: Booking,
    onBackClick: () -> Unit,
    listState: LazyListState
) {
    val scrollOffset by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0) {
                listState.firstVisibleItemScrollOffset.toFloat()
            } else {
                600f
            }
        }
    }

    val imageAlpha = (1f - scrollOffset / 600f).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.spacing300)
    ) {
        AsyncImage(
            model = booking.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = scrollOffset * 0.5f
                    alpha = imageAlpha
                },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(.6f)
                        )
                    )
                )
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(Dimens.spacing16)
                .alpha(imageAlpha)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = Color.White
            )
        }
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(Dimens.spacing16)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = Color.White
            )
        }

        Text(
            text = "${booking.source} - ${booking.destination}",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Dimens.spacing16)
                .alpha(imageAlpha),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun TimelineSection(booking: Booking) {
    Column(modifier = Modifier.padding(Dimens.spacing16)) {
        Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spacing4)) {
            Text(
                text = stringResource(R.string.total_trip),
                color = Color.DarkGray
            )
            Text(
                text = booking.tripDuration,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(Dimens.spacing12))
        booking.timeline.forEach { event ->
            TimelineItem(event)
            Spacer(Modifier.height(Dimens.spacing12))
        }
    }
}

@Composable
private fun TimelineItem(event: TimelineEvent) {
    if (event.type == TimelineType.TRANSFER) {
        TransferCard(event)
    } else {
        FlightTimelineCard(event)
    }
}

@Composable
private fun FlightTimelineCard(event: TimelineEvent) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Dimens.spacing16)
        ) {
            Text(text = event.date, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(Dimens.spacing12))

            Row {
                Text(
                    text = event.scheduledTime,
                    textDecoration = TextDecoration.LineThrough,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.width(Dimens.spacing8))

                Text(
                    text = event.actualTime,
                    color = colorResource(R.color.light_green),
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(Modifier.height(Dimens.spacing8))

            Text(
                text = event.location,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(Dimens.spacing4))

            if (event.status.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(AppConstants.ROUNDED_CORNER_SHAPE),
                    color = colorResource(R.color.bg_green)
                ) {

                    Text(
                        text = event.status,
                        color = colorResource(R.color.light_green),
                        modifier = Modifier.padding(
                            horizontal = Dimens.spacing8,
                            vertical = Dimens.spacing8
                        ),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun TransferCard(event: TimelineEvent) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(R.color.teal_800),
        shape = RoundedCornerShape(Dimens.spacing10)
    ) {
        Text(
            text = stringResource(R.string.transfer_time) + " ${event.transferDuration}",
            modifier = Modifier.padding(
                horizontal = Dimens.spacing16,
                vertical = Dimens.spacing12
            ),
            fontWeight = FontWeight.Bold
        )
    }
}