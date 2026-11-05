package com.travel.klmbookingapp.di

import com.travel.klmbookingapp.data.repository.BookingRepositoryImplementation
import com.travel.klmbookingapp.domain.repository.BookingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookingRepository(impl: BookingRepositoryImplementation): BookingRepository
}