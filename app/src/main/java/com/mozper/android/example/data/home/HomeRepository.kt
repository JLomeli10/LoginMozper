package com.mozper.android.example.data.home

import android.app.Application
import com.google.gson.Gson
import com.mozper.android.example.common.util.Utils
import com.mozper.android.example.data.Result
import com.mozper.android.example.data.model.Employee


class HomeRepository(private val dataSource: HomeDataSource, private val application: Application) {
    private val LIST_EMPLOYE = "list_employes"


    suspend fun getData(): Result<List<Employee>>? {
        // handle login
        var result = dataSource.getData()
        if (result is Result.Success) {
            setEmployesData(result.data)
        }else{
            result = getSaveEmployeesList()
        }
        return result
    }

    private fun setEmployesData(data: List<Employee>) {
        val jsonObject = Gson().toJson(data)
        Utils.setPrefsString(LIST_EMPLOYE, jsonObject, application)
    }

    private fun getSaveEmployeesList(): Result<List<Employee>>? {
        var data = Utils.getPrefsString(LIST_EMPLOYE, application)
        val list = Gson().fromJson(data, mutableListOf<Employee>().javaClass)
        return Result.Success(list)
    }

}