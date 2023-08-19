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
import com.softeer.cartalog.data.model.TypeOption
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
    val navController: LiveData<NavController> = _navController

    private val _typeList: MutableLiveData<List<Type>> = MutableLiveData(emptyList())
    val typeList: LiveData<List<Type>> = _typeList

    private val _selectedType = MutableLiveData(0)
    val selectedType: LiveData<Int> = _selectedType

    private val _hmgData: MutableLiveData<TrimDetail> = MutableLiveData()
    val hmgData: LiveData<TrimDetail> = _hmgData

    private val _selectedPowerTrain: MutableLiveData<PriceData> = MutableLiveData()
    private val _selectedBodyType: MutableLiveData<PriceData> = MutableLiveData()
    private val _selectedWheelDrive: MutableLiveData<PriceData> = MutableLiveData()

    private val _userTotalPrice = MutableLiveData(0)
    val userTotalPrice: LiveData<Int> = _userTotalPrice

    init {
        setTypeData()
        // TODO - 트림 화면에서 넘어온 트림 id로 요청해야함
        setHmgData(2)
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
        _userTotalPrice.value = _userTotalPrice.value?.minus(getTypeData(type).price)
        when (type) {
            ModelTypeSubject.POWERTRAIN -> {
                _powertrain1Selected.value = !_powertrain1Selected.value!!
            }

            ModelTypeSubject.BODYTYPE -> {
                _bodytype1Selected.value = !_bodytype1Selected.value!!
            }

            ModelTypeSubject.WHEELDRIVE -> {
                _wheeldrive1Selected.value = !_wheeldrive1Selected.value!!
            }
        }
        _userTotalPrice.value = _userTotalPrice.value?.plus(getTypeData(type).price)
        setHmgData(2)
    }

    private fun setHmgData(trimId: Int) {
        val selectPowerTrain = if (_powertrain1Selected.value!!) 1 else 2
        val selectBodyType = if (_bodytype1Selected.value!!) 5 else 6
        val selectWheelDrive = if (_wheeldrive1Selected.value!!) 3 else 4

        val modelTypeIds = "$selectPowerTrain,$selectBodyType,$selectWheelDrive"
        viewModelScope.launch {
            _hmgData.value = repository.getTrimDetail(modelTypeIds, trimId)
        }
    }

    suspend fun saveUserSelection() {

        val powerTrain = getTypeData(ModelTypeSubject.POWERTRAIN)
        val bodyType = getTypeData(ModelTypeSubject.BODYTYPE)
        val wheelDrive = getTypeData(ModelTypeSubject.WHEELDRIVE)

        val newPowerTrain = updateData(powerTrain, _selectedPowerTrain.value!!)
        val newBodyType = updateData(bodyType, _selectedBodyType.value!!)
        val newWheelDrive = updateData(wheelDrive, _selectedWheelDrive.value!!)

        repository.saveUserTypeData(newPowerTrain, newBodyType, newWheelDrive)
    }

    private fun setSelectedType() {
        viewModelScope.launch {
            _selectedPowerTrain.value = repository.getTypeData(PriceDataType.POWERTRAIN)
            _selectedBodyType.value = repository.getTypeData(PriceDataType.BODYTYPE)
            _selectedWheelDrive.value = repository.getTypeData(PriceDataType.WHEELDRIVE)

            _powertrain1Selected.value =
                _selectedPowerTrain.value?.optionId == 1 || _selectedPowerTrain.value == null
            _bodytype1Selected.value =
                _selectedBodyType.value?.optionId == 5 || _selectedBodyType.value == null
            _wheeldrive1Selected.value =
                _selectedWheelDrive.value?.optionId == 3 || _selectedWheelDrive.value == null
        }
    }

    fun setUserTotalPrice(price: Int) {
        _userTotalPrice.value = price
    }

    fun setNavController(navController: NavController) {
        _navController.value = navController
    }

    private fun getTypeData(type: ModelTypeSubject): TypeOption {

        when (type) {
            ModelTypeSubject.POWERTRAIN -> {
                return if (powertrain1Selected.value == true) {
                    typeList.value!![0].options[0]
                } else {
                    typeList.value!![0].options[1]
                }
            }

            ModelTypeSubject.BODYTYPE -> {
                return if (bodytype1Selected.value == true) {
                    typeList.value!![1].options[0]
                } else {
                    typeList.value!![1].options[1]
                }
            }

            ModelTypeSubject.WHEELDRIVE -> {
                return if (wheeldrive1Selected.value == true) {
                    typeList.value!![2].options[0]
                } else {
                    typeList.value!![2].options[1]
                }
            }
        }
    }

    private fun updateData(typeOption: TypeOption, priceData: PriceData): PriceData {
        val newData = typeOption.run {
            priceData.copy(optionId = id, name = name, price = price, imgUrl = imageUrl)
        }
        return newData
    }
}