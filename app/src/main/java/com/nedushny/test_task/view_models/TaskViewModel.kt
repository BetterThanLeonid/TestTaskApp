package com.nedushny.test_task.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedushny.test_task.network.Repository
import kotlinx.coroutines.launch
import java.io.IOException

class TaskViewModel : ViewModel() {

    companion object {
        const val TAG = "TaskViewModel"
    }

    private val _data = MutableLiveData<ArrayList<String>>()
    val data: LiveData<ArrayList<String>> get() = _data

    private val _error = MutableLiveData<Any>()
    val error: LiveData<Any> get() = _error

    fun loadImages() {
        viewModelScope.launch {
            try {
                val response = Repository().loadImages()

                if (response.isSuccessful) {
                    _data.postValue(response.body())
                } else {
                    _error.postValue(response.code())
                }

            } catch (e: IOException) {
                _error.postValue(e)
            }
        }
    }
}