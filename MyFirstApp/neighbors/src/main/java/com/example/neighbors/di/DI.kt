package com.example.neighbors.di

import android.app.Application
import com.example.neighbors.dal.repositories.NeighborRepository

object DI {
    lateinit var repository: NeighborRepository
    fun inject(application: Application) {
        repository = NeighborRepository.getInstance(application)
    }
}