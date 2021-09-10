package com.segunfrancis.feature.home

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.segunfrancis.common.util.LoginResult
import com.segunfrancis.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        viewModel.token.observe(viewLifecycleOwner) { result ->
            when(result) {
                is LoginResult.Loading -> handleLoading()
                is LoginResult.Error -> handleError(result.error)
            }
        }

        binding.openRightsButton.setOnClickListener {
            findNavController().navigate("https://com.segunfrancis.feature/myrights".toUri())
        }
    }

    private fun handleLoading() {

    }

    private fun handleError(error: Throwable) {

    }
}
