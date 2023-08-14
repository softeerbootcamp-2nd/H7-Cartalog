package com.softeer.cartalog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.softeer.cartalog.databinding.FragmentTrimBinding
import com.softeer.cartalog.ui.activity.MainActivity
import com.softeer.cartalog.viewmodel.TrimViewModel

class TrimFragment : Fragment() {

    private var _binding: FragmentTrimBinding? = null
    private val binding get() = _binding!!
    private val trimViewModel: TrimViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrimBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = trimViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnChoose.setOnClickListener {
            (activity as MainActivity).changeTab(1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
