package com.softeer.cartalog.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.DialogTypeDetailBinding



/**
 * Dialog 참고용 Code.
 * 현재 화면에서는 사용되고있지 않음
 */

class TypeDetailDialog : DialogFragment() {

    private lateinit var binding: DialogTypeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setStyle(STYLE_NO_FRAME, theme)
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_type_detail, container, true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
//        val width = resources.getDimensionPixelSize(R.dimen.type_detail_dialog_width)
//        val height = resources.getDimensionPixelSize(R.dimen.type_detail_dialog_height)
//        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//        dialog!!.window?.setLayout(width, height)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
