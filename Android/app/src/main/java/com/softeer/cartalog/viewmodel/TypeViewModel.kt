package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.softeer.cartalog.model.data.Type
import com.softeer.cartalog.model.data.TypeHmgData
import com.softeer.cartalog.model.data.TypeOption
import com.softeer.cartalog.model.enums.ModelTypeSubject

class TypeViewModel : ViewModel() {

    private val _powertrain1Selected = MutableLiveData(true)
    val powertrain1Selected = _powertrain1Selected

    private val _bodytype1Selected = MutableLiveData(true)
    val bodytype1Selected = _bodytype1Selected

    private val _wheeldrive1Selected = MutableLiveData(true)
    val wheeldrive1Selected = _wheeldrive1Selected

    private val _navController = MutableLiveData<NavController>()
    val navController = _navController

    // Popup 관련
    private val _typeList: MutableLiveData<List<Type>> by lazy {
        MutableLiveData<List<Type>>(setTrimData())
    }
    val typeList: LiveData<List<Type>> = _typeList

    private val _selectedType = MutableLiveData(0)
    val selectedType = _selectedType

    private fun setTrimData(): List<Type> {
        // 임시 데이터 설정
        val tmpHmgData = listOf(TypeHmgData("최고출력", 202, "3,800", "PS/rpm"))
        val tmpOption = listOf(TypeOption(5, "디젤 2.2", 0, 38, "", "설명", tmpHmgData))
        val tmpData = Type("디젤", tmpOption)
        val tmpData2 = Type("가솔린", tmpOption)

        return listOf(tmpData, tmpData2)
    }

    fun changeSelectedTrim(idx: Int) {
        _selectedType.value = idx
    }

    fun selectType(type: ModelTypeSubject) {
        when (type) {
            ModelTypeSubject.POWERTRAIN -> _powertrain1Selected.value =
                !_powertrain1Selected.value!!

            ModelTypeSubject.BODYTYPE -> _bodytype1Selected.value = !_bodytype1Selected.value!!
            ModelTypeSubject.WHEELDRIVE -> _wheeldrive1Selected.value =
                !_wheeldrive1Selected.value!!
        }
    }

}