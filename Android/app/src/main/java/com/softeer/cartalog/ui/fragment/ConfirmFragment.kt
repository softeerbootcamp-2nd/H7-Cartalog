package com.softeer.cartalog.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.DialogShareBinding
import com.softeer.cartalog.databinding.FragmentConfirmBinding
import com.softeer.cartalog.ui.activity.MainActivity
import com.softeer.cartalog.util.PriceDataCallback
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.ConfirmViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfirmFragment : Fragment() {
    private var _binding: FragmentConfirmBinding? = null
    private val binding get() = _binding!!
    private val carRepositoryImpl by lazy {
        CarRepositoryImpl(
            CarLocalDataSource(MyCarDatabase.getInstance(requireContext())!!), CarRemoteDataSource(
                RetrofitClient.carApi
            )
        )
    }
    private val confirmViewModel: ConfirmViewModel by viewModels {
        CommonViewModelFactory(carRepositoryImpl)
    }
    private var dataCallback: PriceDataCallback? = null
    private var totalPrice: Int = 0

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (activity as MainActivity).changeTab(4)
        }
    }
    private var isReturnedFromEstimate = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        totalPrice = (activity as MainActivity).getUserTotalPrice()
        dataCallback = context as PriceDataCallback
    }

    override fun onResume() {
        super.onResume()
        if (isReturnedFromEstimate) {
            CoroutineScope(Dispatchers.IO).launch {
                confirmViewModel.getOptionDataChanged()
                confirmViewModel.setTotalPriceFromDB()
                withContext(Dispatchers.Main){
                    confirmViewModel.setDetailList()
                }
            }
            isReturnedFromEstimate = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmBinding.inflate(inflater, container, false)
        confirmViewModel.setUserTotalPrice(totalPrice)
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = confirmViewModel

        val dialogBinding = DialogShareBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root).create()

        binding.btnShare.setOnClickListener {
            dialog.show()
        }
        dialogBinding.btnClose.setOnClickListener{
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        isReturnedFromEstimate = true
    }
}