package com.travel.klmbookingapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.travel.klmbookingapp.R
import com.travel.klmbookingapp.core.constants.AppConstants
import com.travel.klmbookingapp.presentation.account.AccountScreen
import com.travel.klmbookingapp.presentation.bookings.BookingScreen
import com.travel.klmbookingapp.presentation.bookings.BookingViewModel
import com.travel.klmbookingapp.presentation.detail.BookingDetailScreen

@Composable
fun RootScreen() {

    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(BottomNavItem.Bookings, BottomNavItem.Account)
    val snackBarHostState = remember { SnackbarHostState() }
    val bookingViewModel: BookingViewModel = hiltViewModel()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            if (currentRoute == DestinationScreen.Bookings.route) {
                FloatingActionButton(
                    onClick = { bookingViewModel.onFabClicked() },
                    containerColor = colorResource(R.color.btn_blue),
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            if (currentRoute == DestinationScreen.Bookings.route ||
                currentRoute == DestinationScreen.Account.route
            ) {
                NavigationBar(containerColor = Color.White) {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                bottomNavController.navigate(item.route) {
                                    popUpTo(bottomNavController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.image,
                                    contentDescription = stringResource(item.titleRes)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(item.titleRes),
                                    color = Color.Black
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = DestinationScreen.Bookings.route,
        ) {
            composable(DestinationScreen.Bookings.route) {
                BookingScreen(
                    viewModel = bookingViewModel,
                    snackBarHostState = snackBarHostState,
                    paddingValues = paddingValues,
                    navigateToDetail = { bookingId ->
                        bottomNavController.navigate(
                            DestinationScreen.BookingDetail.createRoute(bookingId)
                        )
                    }
                )
            }
            composable(DestinationScreen.Account.route) {
                AccountScreen()
            }
            composable(
                DestinationScreen.BookingDetail.route,
                arguments = listOf(
                    navArgument(AppConstants.BOOKING_ID) {
                        type = NavType.StringType
                    }
                )
            ) {
                BookingDetailScreen(
                    onBackClick = { bottomNavController.popBackStack() }
                )
            }
        }
    }
}