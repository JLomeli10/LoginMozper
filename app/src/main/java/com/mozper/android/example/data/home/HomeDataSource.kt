package com.mozper.android.example.data.home

import com.mozper.android.example.api.ApiManagerFactory
import com.mozper.android.example.api.HeaderFactory
import com.mozper.android.example.data.mappers.EmployesMapper
import com.mozper.android.example.data.model.Employee
import com.mozper.android.example.data.Result
import java.lang.Exception

class HomeDataSource {
    var apiService = ApiManagerFactory.makeRetrofitService()
    private var dataEmployees: List<Employee> = listOf()

    suspend fun getData(): Result<List<Employee>>? {
        try {
            val headers = HeaderFactory.getHeaders()
            val result = apiService.getEmployes(
                headers,
                ""
            )
            return if (result.isSuccessful) {
                val data = result.body()!!
                Result.Success(EmployesMapper().map(data))
            } else {
                Result.Error(Exception("Error al cargar datos"))
            }

        } catch (ex: Exception) {
            Result.Error(Exception("Error al cargar datos", ex.cause))
        }
        return null
    }
}