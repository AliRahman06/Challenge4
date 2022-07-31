package com.binar.challenge4.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.binar.challenge4.SharedPreference
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var sharedPref: SharedPreference? = null
    private var status = false

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(view.context)

        Log.d("username_login", sharedPref?.getPrefKey("username").toString())

        status = sharedPref?.getPrefKeyStatus("login_status") == true
        if (status){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.txtRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener{
            var usernameInput = binding.edtUsername.text.toString()
            var passwordInput = binding.edtUsername.text.toString()

            if (binding.edtUsername.text.isNullOrBlank() || binding.edtPassword.text.isNullOrBlank()){
                Snackbar.make(binding.root, "Lengkapi Field Diatas", Snackbar.LENGTH_LONG).show()
            }else{
                val username = sharedPref?.getPrefKey("username")
                val password = sharedPref?.getPrefKey("password")
                if (username.equals(usernameInput) && password.equals(passwordInput)){
                    sharedPref?.saveKeyState(true)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }else{
                    Snackbar.make(binding.root, "User Tidak Ditemukan", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}