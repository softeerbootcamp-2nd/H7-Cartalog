package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.softeer.cartalog.data.model.Type
import com.softeer.cartalog.data.model.TypeHmgData
import com.softeer.cartalog.data.model.TypeOption
import com.softeer.cartalog.data.enums.ModelTypeSubject
import com.softeer.cartalog.data.model.Trim
import com.softeer.cartalog.data.model.TrimDetail
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class TypeViewModel(private val repository: CarRepository) : ViewModel() {

    private val _powertrain1Selected = MutableLiveData(true)
    val powertrain1Selected = _powertrain1Selected

    private val _bodytype1Selected = MutableLiveData(true)
    val bodytype1Selected = _bodytype1Selected

    private val _wheeldrive1Selected = MutableLiveData(true)
    val wheeldrive1Selected = _wheeldrive1Selected

    private val _navController = MutableLiveData<NavController>()
    val navController = _navController

    private val _typeList: MutableLiveData<List<Type>> = MutableLiveData(emptyList())
    val typeList: LiveData<List<Type>> = _typeList

    private val _selectedType = MutableLiveData(0)
    val selectedType = _selectedType

    private val _hmgData: MutableLiveData<TrimDetail> = MutableLiveData()
    val hmgData = _hmgData

    init {
        setTypeData()
        // TODO - 트림 화면에서 넘어온 트림 id로 요청해야함
        setHmgData(2)
    }

    private fun setTypeData() {
        viewModelScope.launch {
            _typeList.value = repository.getTypes()
        }
    }

    fun changeSelectedType(idx: Int) {
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
        setHmgData(2)
    }

    fun setHmgData(trimId: Int) {
        val selectPowerTrain = if (_powertrain1Selected.value!!) 1 else 2
        val selectBodyType = if (_bodytype1Selected.value!!) 5 else 6
        val selectWheelDrive = if (_wheeldrive1Selected.value!!) 3 else 4

        val modelTypeIds = "$selectPowerTrain,$selectBodyType,$selectWheelDrive"
        viewModelScope.launch {
            _hmgData.value = repository.getTrimDetail(modelTypeIds, trimId)
        }
    }

}