package com.mozper.android.example.data.mappers

import com.google.gson.JsonObject
import com.mozper.android.example.data.model.Employee
import vc.reboot.nova.mobile.sabadell.core.repository.mappers.Mapper

class EmployesMapper : Mapper<JsonObject, List<Employee>> {
    override fun map(input: JsonObject): List<Employee> {
        val employees: MutableList<Employee> = arrayListOf()

        val listEmployes = input.getAsJsonArray("employees")
        listEmployes.forEach {
            val element = it.asJsonObject
            employees.add(
                Employee(
                    id = element.get("id").asLong,
                    firstName = element.get("firstName").asString,
                    lastName = element.get("lastName").asString,
                    image = element.get("image").asString,
                    description = element.get("description").asString,
                    rating = element.get("rating").asInt
                )
            )
        }
        return employees
    }
}