package com.softeer.cartalog.ui.dialog

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.tabs.TabLayoutMediator
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.DialogOptionDetailBinding


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

        val tabTitles = listOf("후석 승객 알림", "메탈 리어범퍼스탭", "메탈 도어스커프")
        for (title in tabTitles) {
            val tab = binding.tlOption.newTab().apply { text = title }
            binding.tlOption.addTab(tab)
        }
    }
}
