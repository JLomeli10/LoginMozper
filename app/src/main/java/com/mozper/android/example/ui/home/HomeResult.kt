package com.mozper.android.example.ui.home

import com.mozper.android.example.data.model.Employee

data class HomeResult(
    val success: List<Employee>? = null,
    val error: Int? = null
)