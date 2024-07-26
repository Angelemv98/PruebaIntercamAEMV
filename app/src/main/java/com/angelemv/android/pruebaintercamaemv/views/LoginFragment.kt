package com.angelemv.android.pruebaintercamaemv.views

import LoginViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angelemv.android.pruebaintercamaemv.R
import com.angelemv.android.pruebaintercamaemv.databinding.FragmentMainBinding
import com.angelemv.android.pruebaintercamaemv.models.data.AppDatabase
import com.angelemv.android.pruebaintercamaemv.viewmodel.LoginViewModelFactory
import com.squareup.picasso.Picasso

class LoginFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    // Instanciamos el ViewModel usando el ViewModelFactory
    private val viewModel: LoginViewModel by viewModels {
        val db = AppDatabase.getDatabase(requireContext())
        val usuarioDao = db.usuarioDao()
        LoginViewModelFactory(usuarioDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingConf()
    }

    private fun bindingConf() {
        binding.let {
            Picasso.get().load("https://cdn.icon-icons.com/icons2/3398/PNG/512/android_logo_icon_214673.png").into(it.imageViewIcon)
            it.btnLogin.setOnClickListener {
                val userInput = binding.etUser.text.toString()
                val passwordInput = binding.etPassword.text.toString()
                // Verificar si los campos están vacíos
                if (userInput.isEmpty() || passwordInput.isEmpty()) {
                    if (userInput.isEmpty()) {
                        binding.tilUser.error = "Campos vacíos"
                    } else {
                        binding.tilUser.error = null // Limpiar el error si el campo se llena
                    }

                    if (passwordInput.isEmpty()) {
                        binding.tilPassword.error = "Campos vacíos"
                    } else {
                        binding.tilPassword.error = null // Limpiar el error si el campo se llena
                    }
                    return@setOnClickListener // No continuar si hay campos vacíos
                }

                viewModel.setUser(userInput)
                viewModel.setPassword(passwordInput)

                // Llamar a la función validateUser con un callback
                viewModel.validateUser { valido: Boolean ->
                    if (!valido) {
                        binding.errorLabel.visibility = View.VISIBLE
                        binding.errorLabel.text = "Usuario y contraseña incorrectos, intente de nuevo."
                    } else {
                        binding.tilUser.error = null
                        binding.tilPassword.error = null
                        binding.etUser.setText("")
                        binding.etPassword.setText("")
                        findNavController().navigate(R.id.action_mainFragment_to_listFragment)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}