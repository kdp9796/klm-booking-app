package com.travel.klmbookingapp.presentation.bookings

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.travel.klmbookingapp.R
import com.travel.klmbookingapp.core.constants.AppConstants
import com.travel.klmbookingapp.domain.model.Booking
import com.travel.klmbookingapp.ui.theme.Dimens

@Composable
internal fun BookingPagerContent(
    bookings: List<Booking>,
    onItemClick: (String) -> Unit
) {
    if (bookings.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.spacing16),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_bookings_found),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                bookings,
                key = { it.id }
            ) { booking ->
                BookingCard(
                    booking = booking,
                    onClick = { onItemClick(booking.id) }
                )
            }
        }
    }
}

@Composable
private fun BookingCard(booking: Booking, onClick: () -> Unit) {

    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = Dimens.spacing6),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.spacing12,
                vertical = Dimens.spacing16
            )
            .clickable { onClick() }
    ) {
        Column {
            AsyncImage(
                model = booking.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.spacing180)
            )

            Column(
                modifier = Modifier.padding(Dimens.spacing12),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)
            ) {
                Text(
                    text = "${booking.source} to ${booking.destination}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = booking.departureDate,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(AppConstants.ROUNDED_CORNER_SHAPE),
                        color = colorResource(R.color.btn_blue)
                    ) {
                        Text(
                            text = booking.flightNumber,
                            color = Color.White,
                            modifier = Modifier.padding(
                                horizontal = Dimens.spacing16,
                                vertical = Dimens.spacing8
                            ),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.width(Dimens.spacing20))
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Dimens.spacing4))
                    Text("${booking.passengerCount}")
                }
            }
        }
    }
}