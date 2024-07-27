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
        viewModel.createDefaultUserIfNotExists()
    }

    private fun bindingConf() {
        binding.let {
            Picasso.get().load("https://scontent.fmex34-1.fna.fbcdn.net/v/t39.30808-6/336503741_586003636816113_3726140264424255795_n.png?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_eui2=AeGfIFL2YYVKqEvgMkqP5Di79LVZpd0kw-f0tVml3STD5yXzOo5f4r_MnlsVg-bdOgyZ32FCsbZ90nmKQhOcobnu&_nc_ohc=eFKykVL378oQ7kNvgEz3kQX&_nc_ht=scontent.fmex34-1.fna&oh=00_AYC_cKob_lqpUC9cmNNWafAZPyP_S-LabjRiwYMuaDhg4Q&oe=66AA04AD").into(it.imageViewIcon)
            it.btnLogin.setOnClickListener {
                val userInput = binding.etUser.text.toString()
                val passwordInput = binding.etPassword.text.toString()
                if (userInput.isEmpty() || passwordInput.isEmpty()) {
                    if (userInput.isEmpty()) {
                        binding.tilUser.error = "Campos vacíos"
                    } else {
                        binding.tilUser.error = null
                    }

                    if (passwordInput.isEmpty()) {
                        binding.tilPassword.error = "Campos vacíos"
                    } else {
                        binding.tilPassword.error = null
                    }
                    return@setOnClickListener
                }

                viewModel.setUser(userInput)
                viewModel.setPassword(passwordInput)

                // Llamar a la función validateUser con un callback
                viewModel.validateUser { valido: Boolean ->
                    if (!valido) {
                        binding.errorLabel.visibility = View.VISIBLE
                        binding.errorLabel.text = "Usuario y/o contraseña incorrectos, intente de nuevo."
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