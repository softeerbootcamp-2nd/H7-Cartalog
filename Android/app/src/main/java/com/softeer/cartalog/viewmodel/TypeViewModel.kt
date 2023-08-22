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

    private val _userTotalPrice = MutableLiveData(0)
    val userTotalPrice: LiveData<Int> = _userTotalPrice

    private var selectedPowerTrain: PriceData? = null
    private var selectedBodyType: PriceData? = null
    private var selectedWheelDrive: PriceData? = null

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

        val newPowerTrain = selectedPowerTrain?.let { updateTypeData(powerTrain, it) }
        val newBodyType = selectedBodyType?.let { updateTypeData(bodyType, it) }
        val newWheelDrive = selectedWheelDrive?.let { updateTypeData(wheelDrive, it) }

        if(newPowerTrain != null && newBodyType != null && newWheelDrive != null){
            repository.saveUserTypeData(newPowerTrain, newBodyType, newWheelDrive)
        }
    }

    private fun setSelectedType() {
        viewModelScope.launch {
            selectedPowerTrain = repository.getTypeData(PriceDataType.POWERTRAIN)
            selectedBodyType = repository.getTypeData(PriceDataType.BODYTYPE)
            selectedWheelDrive = repository.getTypeData(PriceDataType.WHEELDRIVE)

            _powertrain1Selected.value =
                selectedPowerTrain?.optionId == 1
            _bodytype1Selected.value =
                selectedBodyType?.optionId == 5
            _wheeldrive1Selected.value =
                selectedWheelDrive?.optionId == 3
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

    private fun updateTypeData(typeOption: TypeOption, priceData: PriceData): PriceData {
        val newData = typeOption.run {
            priceData.copy(optionId = id, name = name, price = price, imgUrl = imageUrl)
        }
        return newData
    }

    fun updateCarData(){
        viewModelScope.launch {
            val old = repository.getMyCarData()
            val update = old!!.copy(
                fuelEfficiency = hmgData.value?.fuelEfficiency,
                displacement = hmgData.value?.displacement,
                totalPrice = userTotalPrice.value!!
            )
            repository.saveUserCarData(update)
        }
    }
}