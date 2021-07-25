package com.example.noodoeapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


class TimeZoneFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()

    private val timeZoneMap = mutableMapOf(
        "timezone" to 13,"UTC +12" to 12,"UTC +11" to 11,"UTC +10" to 10,"UTC +9" to 9,"UTC +8" to 8,"UTC +7" to 7,
        "UTC +6" to 6,"UTC +5" to 5,"UTC +4" to 4,"UTC +3" to 3,"UTC +2" to 2,"UTC +1" to 1,
        "UTC 0" to 0,"UTC -1" to -1,"UTC -2" to -2,"UTC -3" to -3,"UTC -4" to -4,"UTC -5" to -5,
        "UTC -6" to -6,"UTC -7" to -7,"UTC -8" to -8,"UTC -9" to -9,"UTC -10" to -10,"UTC -11" to -11,"UTC -12" to -12)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_zone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<TextView>(R.id.display_email)
        val spinner = view.findViewById<Spinner>(R.id.timezone_spinner)

        "Email: ${viewModel.user.value?.reportEmail}".also { email.text = it }

        viewModel.state.observe(viewLifecycleOwner,{
            Toast.makeText(context,viewModel.state.value, Toast.LENGTH_LONG).show()
        })

        //time zone data
        val arr = ArrayList<String>(timeZoneMap.keys)
        //set up adapter
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,arr)
        spinner.adapter = adapter

        //listener
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val result = timeZoneMap[arr[position]]
                if(result != 13) result?.let { viewModel.updateUserInfo(it) }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

}