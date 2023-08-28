package com.softeer.cartalog.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.softeer.cartalog.R
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.FragmentExteriorBinding
import com.softeer.cartalog.ui.activity.MainActivity
import com.softeer.cartalog.util.PriceDataCallback
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.ExteriorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private var dataCallback: PriceDataCallback? = null
    private var totalPrice: Int = 0

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (activity as MainActivity).changeTab(1)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        totalPrice = (activity as MainActivity).getUserTotalPrice()
        dataCallback = context as PriceDataCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExteriorBinding.inflate(inflater, container, false)
        exteriorViewModel.setUserTotalPrice(totalPrice)
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = exteriorViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnNext.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                saveUserAllData()
                withContext(Dispatchers.Main) {
                    (activity as MainActivity).changeTab(3)
                }
            }
        }
        binding.btnPrev.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                saveUserAllData()
                withContext(Dispatchers.Main) {
                    (activity as MainActivity).changeTab(1)
                }
            }
        }
        binding.btnPriceSummary.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                exteriorViewModel.saveUserSelection()
                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_exteriorFragment_to_priceSummaryBottomSheetFragment)
                }
            }
        }
        exteriorViewModel.userTotalPrice.observe(viewLifecycleOwner) { price ->
            dataCallback?.changeUserTotalPrice(price)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    suspend fun saveUserAllData(){
        exteriorViewModel.saveUserSelection()
        exteriorViewModel.updateCarData()
    }
}