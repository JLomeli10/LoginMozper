package com.mozper.android.example.ui.home

import androidx.lifecycle.*
import com.mozper.android.example.R
import com.mozper.android.example.data.Result
import com.mozper.android.example.data.home.HomeRepository
import com.mozper.android.example.data.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    private var state: SavedStateHandle? = null

    private val _employeeListResult = MutableLiveData<HomeResult>()
    val employeeListResult: LiveData<HomeResult> = _employeeListResult

    val mutableEmployeeItem = MutableLiveData<Employee>()
    val employeeItem: LiveData<Employee> = mutableEmployeeItem


    fun getData() {
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            val result = homeRepository.getData()
            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    _employeeListResult.postValue(HomeResult(success = result.data))

                } else {
                    _employeeListResult.postValue(HomeResult(error = R.string.error_get_employees))
                }
            }
        }
    }

    fun getEmployee(id: Long) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            val result = homeRepository.getData()
            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    mutableEmployeeItem.postValue(result.data.find {
                        it.id.equals(id)
                    })
                } else {
                    _employeeListResult.postValue(HomeResult(error = R.string.error_get_employees))
                }
            }
        }
    }
}