package com.softeer.cartalog.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.DialogTypeDetailPopupBinding
import com.softeer.cartalog.ui.fragment.TypeFragment


class TypeDetailDialog : DialogFragment() {

    private lateinit var binding: DialogTypeDetailPopupBinding
    private val viewModel by lazy {
        val typeFragment =
            requireParentFragment().childFragmentManager.fragments.firstOrNull { it is TypeFragment } as TypeFragment
        typeFragment.typeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setStyle(STYLE_NO_FRAME, theme)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_type_detail_popup, container, true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val layoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog!!.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }

        dialog?.let {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.argb(150, 0, 0, 0)))
            it.window?.attributes = layoutParams
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel
        binding.selectedType = viewModel.selectedType.value

    }
}
