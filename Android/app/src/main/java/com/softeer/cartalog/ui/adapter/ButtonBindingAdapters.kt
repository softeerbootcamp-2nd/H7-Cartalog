package com.softeer.cartalog.ui.adapter

import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.softeer.cartalog.ui.activity.MainActivity

@BindingAdapter("activity", "index")
fun setOnClickTabBtn(
    button: AppCompatButton,
    activity: MainActivity,
    idx: Int
){
    button.setOnClickListener {
        activity.changeTab(idx)
    }
}