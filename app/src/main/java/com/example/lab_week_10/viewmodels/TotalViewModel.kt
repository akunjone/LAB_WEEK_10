package com.example.lab_week_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {
    var _total = MutableLiveData<Int>(0)
    var total: LiveData<Int> = _total

    fun incrementTotal(): MutableLiveData<Int> {
        _total.value = (_total.value ?: 0)+1
        return _total
    }
}