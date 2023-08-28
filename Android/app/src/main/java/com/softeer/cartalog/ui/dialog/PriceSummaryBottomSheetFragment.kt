package com.softeer.cartalog.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.FragmentPriceSummaryBottomSheetBinding
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.PriceSummaryViewModel

class PriceSummaryBottomSheetFragment() : BottomSheetDialogFragment() {

    private var _binding: FragmentPriceSummaryBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val carRepositoryImpl by lazy {
        CarRepositoryImpl(
            CarLocalDataSource(MyCarDatabase.getInstance(requireContext())!!),
            CarRemoteDataSource(RetrofitClient.carApi)
        )
    }
    private val priceSummaryViewModel: PriceSummaryViewModel by viewModels {
        CommonViewModelFactory(carRepositoryImpl)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPriceSummaryBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.viewModel = priceSummaryViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnCloseBottom.setOnClickListener {
            dismiss()
        }
        binding.btnCloseTop.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}