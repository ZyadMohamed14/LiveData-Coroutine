package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val mlivedata = MutableLiveData("data")
    val liveData: LiveData<String> = mlivedata

    private val mstateflow = MutableStateFlow("data")
    val stateFlow = mstateflow.asStateFlow()

    private val msharedflow = MutableSharedFlow<String>()
    val sharedFlow = msharedflow.asSharedFlow()

    fun setDataFromLiveData() {
        mlivedata.value = "LiveData"
    }
    fun setDataFromStateFlow() {
        mstateflow.value = "Stateflow"
    }
    fun setDataFromFlow(): Flow<Int> {
        return flow {
            repeat(6) {
                emit(it)
                delay(1000)
            }
        }
    }
    fun setDataFromSharedStateFlow() {
        viewModelScope.launch { msharedflow.emit("Shared flow") }
    }
}