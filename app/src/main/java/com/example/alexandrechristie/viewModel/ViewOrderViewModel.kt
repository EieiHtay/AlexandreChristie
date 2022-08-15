package com.example.alexandrechristie.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.alexandrechristie.data.entity.ViewOrder
import com.example.alexandrechristie.data.entity.Watch
import com.example.alexandrechristie.data.repository.ViewOrderRepository
import kotlinx.coroutines.launch

class ViewOrderViewModel(application: Application):AndroidViewModel(application) {
    private val repository : ViewOrderRepository by lazy { ViewOrderRepository(application) }

    fun insertViewOrderForm(viewOrder: ViewOrder){
        viewModelScope.launch {
            repository.insertViewOrder(viewOrder)
        }
    }
    fun getViewOrderByEmail(email:String): LiveData<List<ViewOrder>> {
        return repository.getViewOrderListByEmail(email)
    }
}