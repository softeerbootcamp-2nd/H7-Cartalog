package com.softeer.cartalog.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.DialogTypeDetailBinding
import com.softeer.cartalog.databinding.FragmentTypeBinding
import com.softeer.cartalog.viewmodel.TypeViewModel

/**
 * DialogFragment 공식문서 ->
 * https://developer.android.com/guide/fragments/dialogs
 */
class TypeDetailDialog : DialogFragment() {

    private lateinit var binding: DialogTypeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_type_detail, container, true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
