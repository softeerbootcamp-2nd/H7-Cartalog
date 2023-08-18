package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.softeer.cartalog.data.enums.ModelTypeSubject
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.TrimDetail
import com.softeer.cartalog.data.model.Type
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class TypeViewModel(private val repository: CarRepository) : ViewModel() {

    private val _powertrain1Selected = MutableLiveData(true)
    val powertrain1Selected: LiveData<Boolean> = _powertrain1Selected

    private val _bodytype1Selected = MutableLiveData(true)
    val bodytype1Selected: LiveData<Boolean> = _bodytype1Selected

    private val _wheeldrive1Selected = MutableLiveData(true)
    val wheeldrive1Selected: LiveData<Boolean> = _wheeldrive1Selected

    private val _navController = MutableLiveData<NavController>()
    val navController: MutableLiveData<NavController> = _navController

    private val _typeList: MutableLiveData<List<Type>> = MutableLiveData(emptyList())
    val typeList: LiveData<List<Type>> = _typeList

    private val _selectedType = MutableLiveData(0)
    val selectedType: LiveData<Int> = _selectedType

    private val _hmgData: MutableLiveData<TrimDetail> = MutableLiveData()
    val hmgData: LiveData<TrimDetail> = _hmgData

    private val selectedPowerTrain: MutableLiveData<PriceData> = MutableLiveData()
    private val selectedBodyType: MutableLiveData<PriceData> = MutableLiveData()
    private val selectedWheelDrive: MutableLiveData<PriceData> = MutableLiveData()

    init {
        setTypeData()
        // TODO - 트림 화면에서 넘어온 트림 id로 요청해야함
        setHmgData(2)

        // 선택된 데이터 불러오기
        setSelectedType()
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

    suspend fun saveUserSelection() {

        val powerTrain = if (powertrain1Selected.value == true) {
            typeList.value?.get(0)?.options?.get(0)
        } else {
            typeList.value?.get(0)?.options?.get(1)
        }
        val bodyType = if (bodytype1Selected.value == true) {
            typeList.value?.get(1)?.options?.get(0)
        } else {
            typeList.value?.get(1)?.options?.get(1)
        }
        val wheelDrive = if (wheeldrive1Selected.value == true) {
            typeList.value?.get(2)?.options?.get(0)
        } else {
            typeList.value?.get(2)?.options?.get(1)
        }

        val newPowerTrain = powerTrain?.let {
            selectedPowerTrain.value?.copy(
                optionId = it.id, name = it.name, price = it.price, imgUrl = it.imageUrl
            )
        }
        val newBodyType = bodyType?.let {
            selectedBodyType.value?.copy(
                optionId = it.id, name = it.name, price = it.price, imgUrl = it.imageUrl
            )
        }
        val newWheelDrive = wheelDrive?.let {
            selectedWheelDrive.value?.copy(
                optionId = it.id, name = it.name, price = it.price, imgUrl = it.imageUrl
            )
        }
        repository.saveUserTypeData(newPowerTrain!!,newBodyType!!,newWheelDrive!!)
    }

    private fun setSelectedType(){
        viewModelScope.launch {
            selectedPowerTrain.value = repository.getTypeData(PriceDataType.POWERTRAIN)
            selectedBodyType.value = repository.getTypeData(PriceDataType.BODYTYPE)
            selectedWheelDrive.value = repository.getTypeData(PriceDataType.WHEELDRIVE)

            _powertrain1Selected.value = selectedPowerTrain.value?.optionId == 1 || selectedPowerTrain.value == null
            _bodytype1Selected.value = selectedBodyType.value?.optionId == 5 || selectedPowerTrain.value == null
            _wheeldrive1Selected.value = selectedWheelDrive.value?.optionId == 3 || selectedPowerTrain.value == null
        }
    }
}