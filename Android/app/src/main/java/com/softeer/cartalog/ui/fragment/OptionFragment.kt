package com.softeer.cartalog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.softeer.cartalog.databinding.FragmentOptionBinding
import com.softeer.cartalog.databinding.FragmentTrimBinding
import com.softeer.cartalog.viewmodel.InteriorViewModel
import com.softeer.cartalog.viewmodel.OptionViewModel

class OptionFragment: Fragment() {
    private val optionViewModel: OptionViewModel by viewModels()
    private var _binding: FragmentOptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = optionViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}