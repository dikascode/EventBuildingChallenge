package com.dikascode.eventbuildingchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dikascode.eventbuildingchallenge.repository.EventRepository
import com.dikascode.eventbuildingchallenge.viewmodel.EventViewModel

class ViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
