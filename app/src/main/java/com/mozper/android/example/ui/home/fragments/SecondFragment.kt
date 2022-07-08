package com.mozper.android.example.ui.home.fragments

import android.R.attr.defaultValue
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mozper.android.example.R
import com.mozper.android.example.data.model.Employee
import com.mozper.android.example.databinding.FragmentSecondBinding
import com.mozper.android.example.ui.home.HomeViewModel
import com.mozper.android.example.ui.home.HomeViewModelFactory
import com.squareup.picasso.Picasso


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(requireActivity().application)
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            val idEmployee = bundle.getLong("id_employee", defaultValue.toLong())
            homeViewModel.getEmployee(idEmployee)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrieveResponseServices()
    }

    private fun retrieveResponseServices() {
        homeViewModel.employeeItem.observe(viewLifecycleOwner, Observer {
            val employeesResult = it ?: return@Observer
            if (employeesResult != null) {
                initView(employeesResult)
            }
        })
    }


    private fun initView(employee: Employee?) {
        if (employee != null) {
            binding.employeeName.text = employee.firstName + " " + employee.lastName
            Picasso.get().load(employee.image).into( binding.employeeImage);
            binding.employeeDescription.text = employee.description
            binding.employeeRate.text = employee.rating.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}