package com.softeer.cartalog.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.softeer.cartalog.R
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.FragmentOptionBinding
import com.softeer.cartalog.ui.activity.MainActivity
import com.softeer.cartalog.util.PriceDataCallback
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.OptionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OptionFragment : Fragment() {
    private var _binding: FragmentOptionBinding? = null
    private val binding get() = _binding!!

    private val carRepositoryImpl by lazy {
        CarRepositoryImpl(
            CarLocalDataSource(MyCarDatabase.getInstance(requireContext())!!), CarRemoteDataSource(
                RetrofitClient.carApi
            )
        )
    }
    private val optionViewModel: OptionViewModel by viewModels {
        CommonViewModelFactory(carRepositoryImpl)
    }
    private var dataCallback: PriceDataCallback? = null
    private var totalPrice: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        totalPrice = (activity as MainActivity).getUserTotalPrice()
        dataCallback = context as PriceDataCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionBinding.inflate(inflater, container, false)
        optionViewModel.setUserTotalPrice(totalPrice)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = optionViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnNext.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                optionViewModel.saveUserSelection()
                withContext(Dispatchers.Main) {
                    (activity as MainActivity).changeTab(5)
                }
            }
        }
        binding.btnPrev.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                optionViewModel.saveUserSelection()
                withContext(Dispatchers.Main) {
                    (activity as MainActivity).changeTab(3)
                }
            }
        }
        binding.btnPriceSummary.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                optionViewModel.saveUserSelection()
                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_optionFragment_to_priceSummaryBottomSheetFragment)
                }
                optionViewModel.getUserDataFromDB()
            }
        }
        optionViewModel.userTotalPrice.observe(viewLifecycleOwner) { price ->
            dataCallback?.changeUserTotalPrice(price)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}