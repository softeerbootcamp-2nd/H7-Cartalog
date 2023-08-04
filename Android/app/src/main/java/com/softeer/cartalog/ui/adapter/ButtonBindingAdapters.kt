package com.softeer.cartalog.ui.adapter

import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.softeer.cartalog.R
import com.softeer.cartalog.model.enums.ModelTypeSubject
import com.softeer.cartalog.ui.dialog.TypeDetailDialog
import com.softeer.cartalog.viewmodel.TypeViewModel

@BindingAdapter("onClickShowDialog")
fun onClickShowDialog(view: View, viewModel: TypeViewModel) {
    //TypeDetailDialog().show(supportFragmentManager, "TypeDetailDialog")
}

