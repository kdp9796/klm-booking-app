package com.travel.klmbookingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.travel.klmbookingapp.presentation.navigation.RootScreen
import com.travel.klmbookingapp.ui.theme.KLMBookingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookingApp()
        }
    }
}

@Composable
fun BookingApp() {
    KLMBookingAppTheme {
        RootScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookingApp()
}