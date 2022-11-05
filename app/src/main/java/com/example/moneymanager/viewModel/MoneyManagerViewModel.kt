package com.example.moneymanager.viewModel

import androidx.lifecycle.*
import com.example.moneymanager.database.Item
import com.example.moneymanager.database.ItemDao
import kotlinx.coroutines.launch

class MoneyManagerViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    fun isEntryValid(activity: String, date: String, amount: String): Boolean {
        if (activity.isBlank() || date.isBlank() || amount.isBlank()) {
            return false
        }
        return true
    }

    private fun getNewItemEntry(activity: String, date: String, amount: String): Item {
        return Item(
            activity = activity,
            date = date,
            amount = amount.toDouble()
        )
    }

    fun addNewItem(activity: String, date: String, amount: String) {
        val newItem = getNewItemEntry(activity, date, amount)
        insertItem(newItem)
    }

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }
}

class MoneyManagerViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoneyManagerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MoneyManagerViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}