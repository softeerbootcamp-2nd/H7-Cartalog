package com.softeer.cartalog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.FragmentExteriorBinding
import com.softeer.cartalog.ui.activity.MainActivity
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.ExteriorViewModel

class ExteriorFragment : Fragment() {
    private var _binding: FragmentExteriorBinding? = null
    private val binding get() = _binding!!

    private val carRepositoryImpl by lazy {
        CarRepositoryImpl(
            CarLocalDataSource(MyCarDatabase.getInstance(requireContext())!!), CarRemoteDataSource(
                RetrofitClient.carApi
            )
        )
    }
    private val exteriorViewModel: ExteriorViewModel by viewModels {
        CommonViewModelFactory(carRepositoryImpl)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExteriorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = exteriorViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnNext.setOnClickListener {
            (activity as MainActivity).changeTab(3)
        }
        binding.btnPrev.setOnClickListener {
            (activity as MainActivity).changeTab(1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}