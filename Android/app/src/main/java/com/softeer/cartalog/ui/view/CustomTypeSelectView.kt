package com.softeer.cartalog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.get
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.CustomTypeselectBinding
import com.softeer.cartalog.model.enums.ModelTypeSubject
import com.softeer.cartalog.viewmodel.TypeViewModel

class CustomTypeSelectView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var binding: CustomTypeselectBinding
    private lateinit var modelType: ModelTypeSubject
    private var selected: Int
    private val viewModel by lazy {
        ViewModelProvider(findViewTreeViewModelStoreOwner()!!).get<TypeViewModel>()
    }

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomTypeSelectView, 0, 0)
        val modelTypeValue = typedArray.getInt(R.styleable.CustomTypeSelectView_modelType, 0)
        selected = typedArray.getInt(R.styleable.CustomTypeSelectView_selected, 0)
        typedArray.recycle()

        modelType = when (modelTypeValue) {
            0 -> ModelTypeSubject.POWERTRAIN
            1 -> ModelTypeSubject.BODYTYPE
            2 -> ModelTypeSubject.WHEELDRIVE
            else -> ModelTypeSubject.POWERTRAIN
        }

        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.custom_typeselect,this, true)
        setInit()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.lifecycleOwner = findViewTreeLifecycleOwner()
        binding.viewModel = viewModel
        binding.type = modelType
        binding.selected = selected

    }

    fun setInit(){
        when(modelType){
            // TODO: Hardcoded for TEST!!
            ModelTypeSubject.POWERTRAIN -> {
            }
            ModelTypeSubject.BODYTYPE -> {
                binding.btnDetailView.visibility = VISIBLE
                binding.btnHmgData.visibility = GONE
            }
            ModelTypeSubject.WHEELDRIVE -> {
                binding.btnDetailView.visibility = VISIBLE
                binding.btnHmgData.visibility = GONE
            }
        }
    }

}
