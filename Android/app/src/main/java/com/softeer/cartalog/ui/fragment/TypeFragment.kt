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
import com.softeer.cartalog.databinding.FragmentTypeBinding
import com.softeer.cartalog.ui.activity.MainActivity
import com.softeer.cartalog.util.PriceDataCallback
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.TypeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TypeFragment : Fragment() {
    private var _binding: FragmentTypeBinding? = null
    private val binding get() = _binding!!

    private val carRepositoryImpl by lazy {
        CarRepositoryImpl(
            CarLocalDataSource(MyCarDatabase.getInstance(requireContext())!!),
            CarRemoteDataSource(RetrofitClient.carApi)
        )
    }
    val typeViewModel: TypeViewModel by viewModels {
        CommonViewModelFactory(carRepositoryImpl)
    }
    private var dataCallback: PriceDataCallback? = null
    private var totalPrice: Int = 0

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (activity as MainActivity).changeTab(0)
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
        _binding = FragmentTypeBinding.inflate(inflater, container, false)
        typeViewModel.setUserTotalPrice(totalPrice)
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = typeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnNext.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                saveUserAllData()
                withContext(Dispatchers.Main){
                    (activity as MainActivity).changeTab(2)
                }
            }
        }
        binding.btnPrev.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                saveUserAllData()
                withContext(Dispatchers.Main){
                    (activity as MainActivity).changeTab(0)
                }
            }
        }
        binding.btnPriceSummary.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                typeViewModel.saveUserSelection()
                withContext(Dispatchers.Main){
                    findNavController().navigate(R.id.action_typeFragment_to_priceSummaryBottomSheetFragment)
                }
            }
        }
        typeViewModel.userTotalPrice.observe(viewLifecycleOwner) { price ->
            dataCallback?.changeUserTotalPrice(price)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    suspend fun saveUserAllData(){
        typeViewModel.saveUserSelection()
        typeViewModel.updateCarData()
    }
}