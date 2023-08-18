package com.softeer.cartalog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softeer.cartalog.data.repository.CarRepository

class CommonViewModelFactory(private val repository: CarRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TrimViewModel::class.java) -> TrimViewModel(repository) as T
            modelClass.isAssignableFrom(TypeViewModel::class.java) -> TypeViewModel(repository) as T
            modelClass.isAssignableFrom(PriceSummaryViewModel::class.java) -> PriceSummaryViewModel(repository) as T
            modelClass.isAssignableFrom(ExteriorViewModel::class.java) -> ExteriorViewModel(repository) as T
            modelClass.isAssignableFrom(InteriorViewModel::class.java) -> InteriorViewModel(repository) as T
            modelClass.isAssignableFrom(OptionViewModel::class.java) -> OptionViewModel(repository) as T

            // ... 여러 뷰모델 추가
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
