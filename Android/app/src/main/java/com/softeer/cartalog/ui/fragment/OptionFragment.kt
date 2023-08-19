package com.softeer.cartalog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.FragmentOptionBinding
import com.softeer.cartalog.ui.activity.MainActivity
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
        binding.btnNext.setOnClickListener {
            (activity as MainActivity).changeTab(5)
        }
        binding.btnPrev.setOnClickListener {
            (activity as MainActivity).changeTab(3)
        }
        binding.btnPriceSummary.setOnClickListener {
            findNavController().navigate(R.id.action_optionFragment_to_priceSummaryBottomSheetFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}