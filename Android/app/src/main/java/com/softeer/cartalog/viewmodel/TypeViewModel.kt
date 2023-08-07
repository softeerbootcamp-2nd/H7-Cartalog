package com.softeer.cartalog.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softeer.cartalog.R
import com.softeer.cartalog.model.enums.ModelTypeSubject

class TypeViewModel : ViewModel() {

    private val _powertrain1Selected = MutableLiveData<Boolean>(true)
    val powertrain1Selected = _powertrain1Selected

    private val _bodytype1Selected = MutableLiveData<Boolean>(true)
    val bodytype1Selected = _bodytype1Selected

    private val _wheeldrive1Selected = MutableLiveData<Boolean>(true)
    val wheeldrive1Selected = _wheeldrive1Selected

    fun selectType(type: ModelTypeSubject){
        when(type){
            ModelTypeSubject.POWERTRAIN -> _powertrain1Selected.value = !_powertrain1Selected.value!!
            ModelTypeSubject.BODYTYPE -> _bodytype1Selected.value = !_bodytype1Selected.value!!
            ModelTypeSubject.WHEELDRIVE -> _wheeldrive1Selected.value = !_wheeldrive1Selected.value!!
        }
    }

}