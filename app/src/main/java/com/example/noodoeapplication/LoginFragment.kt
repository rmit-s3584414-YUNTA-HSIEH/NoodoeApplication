package com.example.noodoeapplication

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController


class LoginFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.login_button)
        val email = view.findViewById<EditText>(R.id.email)
        val password = view.findViewById<EditText>(R.id.password)

        button.setOnClickListener {
            //check empty text
            if(email.text.toString().trim() == "" || password.text.toString().trim() == ""){
                Toast.makeText(context,"email/password connot be empty",Toast.LENGTH_LONG).show()
            }

            //validate email address
            if(email.text.toString().isValidEmail()){
                viewModel.getUserInfo(email.text.toString(),password.text.toString())
            }else{
                Toast.makeText(context,"wrong email format",Toast.LENGTH_LONG).show()
            }
        }
        //show the state of login
        viewModel.state.observe(viewLifecycleOwner, {
            Toast.makeText(context,viewModel.state.value,Toast.LENGTH_LONG).show()
        })
        //SUCCESSFULLY LOGIN - jump to time zone fragment
        viewModel.user.observe(viewLifecycleOwner,{
            findNavController().navigate(R.id.action_loginFragment_to_timeZoneFragment)
        })



    }

    //validate email address
    private fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}