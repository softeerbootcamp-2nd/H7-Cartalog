package com.softeer.cartalog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.softeer.cartalog.databinding.FragmentTrimBinding
import com.softeer.cartalog.databinding.FragmentTypeBinding

class TypeFragment: Fragment() {

    private var _binding: FragmentTypeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //findNavController().navigate(R.id.secondFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}