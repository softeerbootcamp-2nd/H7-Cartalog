package com.softeer.cartalog.ui.dialog

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.softeer.cartalog.R
import com.softeer.cartalog.data.enums.OptionMode
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.DialogOptionDetailBinding
import com.softeer.cartalog.ui.adapter.OptionDefaultAdapter
import com.softeer.cartalog.ui.adapter.OptionSelectAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.filterList


class OptionDetailDialog : DialogFragment() {

    private lateinit var binding: DialogOptionDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setStyle(STYLE_NO_FRAME, theme)
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_option_detail, container, true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.option_detail_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.option_detail_dialog_height)
        dialog!!.window?.setLayout(width, height)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnClose.setOnClickListener { findNavController().popBackStack() }

        val optionId = arguments?.getString("optionId")
        val repository = CarRepositoryImpl(
            CarLocalDataSource(MyCarDatabase.getInstance(requireContext())!!),
            CarRemoteDataSource(RetrofitClient.carApi)
        )

        optionId?.let {
            lifecycleScope.launch {
                val options = repository.getDetailOptions(optionId)

                if (options.options != null){
                    val tabTitles = options.options.map { it.name }
                    for (title in tabTitles) {
                        val tab = binding.tlOption.newTab().apply { text = title }
                        binding.tlOption.addTab(tab)
                    }

                    binding.option = options.options[0]
                    binding.tlOption.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            binding.option = options.options[tab?.position!!]
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {}
                        override fun onTabReselected(tab: TabLayout.Tab?) {}
                    })
                } else {
                    binding.option = options
                }


            }
        }



    }
}
