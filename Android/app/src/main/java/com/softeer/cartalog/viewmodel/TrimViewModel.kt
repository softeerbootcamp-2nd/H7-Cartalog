package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softeer.cartalog.data.model.Trim

class TrimViewModel : ViewModel() {

    private val _trimList: MutableLiveData<List<Trim>> by lazy {
        MutableLiveData<List<Trim>>(setTrimData())
    }
    val trimList: LiveData<List<Trim>> = _trimList

    private val _selectedTrim = MutableLiveData<Int>(0)
    val selectedTrim = _selectedTrim

    private fun setTrimData(): List<Trim>{
        // 나중에 defaultInfo non-null로 설정하기
        val tmpTrim = Trim(1,"Exclusive","기본에 충실한 팰리세이드",41980000,41980000,"","","",null,null)
        return listOf(tmpTrim)
    }

    fun changeSelectedTrim(idx: Int){
        _selectedTrim.value = idx
    }
}