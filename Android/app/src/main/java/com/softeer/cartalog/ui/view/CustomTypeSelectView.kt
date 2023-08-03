package com.softeer.cartalog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.CustomTypeselectBinding
import com.softeer.cartalog.model.enums.ModelTypeSubject

class CustomTypeSelectView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var binding: CustomTypeselectBinding
    private lateinit var modelType: ModelTypeSubject

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomTypeSelectView, 0, 0)
        val modelTypeValue = typedArray.getInt(R.styleable.CustomTypeSelectView_modelType, 0)
        typedArray.recycle()

        modelType = when (modelTypeValue) {
            0 -> ModelTypeSubject.POWERTRAIN
            1 -> ModelTypeSubject.BODYTYPE
            2 -> ModelTypeSubject.WHEELDRIVE
            else -> ModelTypeSubject.POWERTRAIN
        }

        binding = CustomTypeselectBinding.inflate(LayoutInflater.from(context),this, true)

        setInit()
    }

    fun setInit(){
        when(modelType){
            // TODO: Hardcoded for TEST!!
            ModelTypeSubject.POWERTRAIN -> {
                binding.tvTypeName.text = "디젤 2.2"
            }
            ModelTypeSubject.BODYTYPE -> {
                binding.btnDetailView.visibility = VISIBLE
                binding.btnHmgData.visibility = GONE
                binding.tvTypeName.text = "7인승"
            }
            ModelTypeSubject.WHEELDRIVE -> {
                binding.tvTypeName.text = "2WD"
            }
        }
    }

}
