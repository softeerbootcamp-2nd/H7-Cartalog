package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softeer.cartalog.model.data.Trim
import com.softeer.cartalog.model.data.TrimOption

class TrimViewModel : ViewModel() {

    private val _trimList: MutableLiveData<List<Trim>> by lazy {
        MutableLiveData<List<Trim>>(setTrimData())
    }
    val trimList: LiveData<List<Trim>> = _trimList

    private val _selectedTrim = MutableLiveData<Int>(0)
    val selectedTrim = _selectedTrim

    private fun setTrimData(): List<Trim>{
        // 임시 데이터 설정
        val tmpOption = listOf(TrimOption("a",12))
        val tmpData = Trim("Le Blanc","desc",10,tmpOption)
        val tmpData2 = Trim("Car ","desc",10,tmpOption)
        return listOf(tmpData,tmpData2,tmpData,tmpData2)
    }

    fun changeSelectedTrim(idx: Int){
        _selectedTrim.value = idx
    }
}