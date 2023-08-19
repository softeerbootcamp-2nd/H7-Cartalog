package com.softeer.cartalog.ui.fragment

import android.content.Context
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
import com.softeer.cartalog.databinding.FragmentTrimBinding
import com.softeer.cartalog.ui.activity.MainActivity
import com.softeer.cartalog.util.PriceDataCallback
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.TrimViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrimFragment : Fragment() {

    private var _binding: FragmentTrimBinding? = null
    private val binding get() = _binding!!
    private val carRepositoryImpl by lazy {
        CarRepositoryImpl(
            CarLocalDataSource(MyCarDatabase.getInstance(requireContext())!!),
            CarRemoteDataSource(RetrofitClient.carApi)
        )
    }
    private val trimViewModel: TrimViewModel by viewModels {
        CommonViewModelFactory(carRepositoryImpl)
    }
    private var dataCallback: PriceDataCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PriceDataCallback) {
            dataCallback = context
        } else {
            throw RuntimeException("$context must implement DataCallback")
        }
    }

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
            dataCallback?.onInitPriceDataReceived(trimViewModel.getPriceData())
            CoroutineScope(Dispatchers.Main).launch {
                trimViewModel.setInitialMyCarData()
                (activity as MainActivity).changeTab(1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
