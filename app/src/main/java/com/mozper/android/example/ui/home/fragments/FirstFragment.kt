package com.mozper.android.example.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozper.android.example.R
import com.mozper.android.example.data.model.Employee
import com.mozper.android.example.databinding.FragmentFirstBinding
import com.mozper.android.example.ui.home.EmployeListAdapter
import com.mozper.android.example.ui.home.HomeViewModel
import com.mozper.android.example.ui.home.HomeViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(requireActivity().application)
    }


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        retrieveResponseServices()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getData()
    }

    private fun retrieveResponseServices() {
        homeViewModel.employeeListResult.observe(viewLifecycleOwner, Observer {
            val employeesResult = it ?: return@Observer
            if (employeesResult.error != null) {
                Toast.makeText(requireContext(), "fail", Toast.LENGTH_LONG)
            }
            if (employeesResult.success != null) {
                binding.textviewFirst.visibility = View.INVISIBLE
                setRecyclerView(employeesResult.success)
            }
        })
    }

    private fun setRecyclerView(data: List<Employee>){
        binding?.recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = EmployeListAdapter(data) { employee -> adapterOnClick(employee) }
        }
    }

    /* Opens EmployeDetailFragment when RecyclerView item is clicked. */
    private fun adapterOnClick(employee: Employee) {
        val bundle = Bundle()
        bundle.putLong("id_employee", employee.id)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}