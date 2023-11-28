package com.dikascode.eventbuildingchallenge.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dikascode.eventbuildingchallenge.state.EventIntent
import com.dikascode.eventbuildingchallenge.state.EventState
import com.dikascode.eventbuildingchallenge.model.Item
import com.dikascode.eventbuildingchallenge.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository) : ViewModel() {
    private val _state = MutableLiveData<EventState>()
    val state: LiveData<EventState> = _state

    private val _isLoadingItems = MutableLiveData<Boolean>(false)
    val isLoadingItems: LiveData<Boolean> = _isLoadingItems

    // Holds the list of items added by the user
    private val _userItems = MutableLiveData<MutableList<Item>>(mutableListOf())
    val userItems: LiveData<MutableList<Item>> = _userItems

    // This will hold the sum of min and max budgets of selected items
    private val _selectedBudget = MutableLiveData<Pair<Int, Int>>(0 to 0)
    val selectedBudget: LiveData<Pair<Int, Int>> = _selectedBudget

    init {
        processIntents(EventIntent.LoadData)
    }

    fun processIntents(intent: EventIntent) {
        when (intent) {
            is EventIntent.LoadData -> loadData()
            is EventIntent.AddItemToUserList -> addItemToList(intent.item)
            is EventIntent.FetchItems -> fetchItemsForCategory(intent.categoryId)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = EventState(isLoading = true)
            try {
                val categories = repository.getCategories()
                _state.value = EventState(categories = categories)
            } catch (e: Exception) {
                _state.value = EventState(error = e.localizedMessage ?: "Error")
            }
        }
    }

    // function to add item to list
    fun addItemToList(item: Item) {
        val currentItems = _userItems.value ?: mutableListOf()
        if (currentItems.any { it.id == item.id }) {
            // Item is already added, so remove it
            currentItems.removeAll { it.id == item.id }
        } else {
            // Item is not added, so add it
            currentItems.add(item.copy(isAdded = true))
        }
        _userItems.value = currentItems
        calculateBudget()
        updateItemStateInCategory(item)
    }

    private fun updateItemStateInCategory(updatedItem: Item) {
        _state.value = _state.value?.let { state ->
            state.copy(categories = state.categories.map { category ->
                category.copy(items = category.items.map { item ->
                    if (item.id == updatedItem.id) updatedItem else item
                })
            })
        }
    }

    // Recalculate the budget whenever the list of selected items changes
    private fun calculateBudget() {
        _userItems.value?.let { items ->
            val totalMinBudget = items.sumOf { it.minBudget }
            val totalMaxBudget = items.sumOf { it.maxBudget }
            _selectedBudget.value = totalMinBudget to totalMaxBudget
        }
    }

    fun fetchItemsForCategory(categoryId: Int) {
        _isLoadingItems.value = true
        viewModelScope.launch {
            try {
                val items = repository.getItemsForCategory(categoryId)

                //update state
                _state.value = EventState(categoryItems = items)
                _isLoadingItems.value = false
            } catch (e: Exception) {
                _isLoadingItems.value = false
                _state.value =
                    _state.value?.copy(error = e.localizedMessage ?: "Error fetching items")
            }
        }
    }
}
