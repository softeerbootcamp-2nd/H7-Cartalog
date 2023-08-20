package com.softeer.cartalog.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.R
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.DialogOptionDetailBinding
import kotlinx.coroutines.launch


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
        //val height = WindowManager.LayoutParams.WRAP_CONTENT
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

                if (options.options != null) {
                    val tabTitles = options.options.map { it.name }
                    for (title in tabTitles) {
                        val tab = binding.tlOption.newTab().apply { text = title }
                        binding.tlOption.addTab(tab)
                    }

                    binding.option = options.options[0]
                    binding.tlOption.addOnTabSelectedListener(object :
                        TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            binding.option = options.options[tab?.position!!]
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {}
                        override fun onTabReselected(tab: TabLayout.Tab?) {}
                    })
                } else {
                    binding.tlOption.visibility = View.GONE
                    binding.option = options
                }


            }
        }


    }
}
