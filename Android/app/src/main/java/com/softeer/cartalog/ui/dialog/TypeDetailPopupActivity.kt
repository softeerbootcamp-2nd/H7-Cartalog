package com.softeer.cartalog.ui.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.ActivityMainBinding
import com.softeer.cartalog.databinding.ActivityTypeDetailPopupBinding
import com.softeer.cartalog.viewmodel.MainViewModel
import com.softeer.cartalog.viewmodel.TypeViewModel
import java.lang.reflect.Type

class TypeDetailPopupActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityTypeDetailPopupBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_type_detail_popup)

    }
}