package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.enums.OptionMode
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.Option
import com.softeer.cartalog.data.model.Options
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class OptionViewModel(private val repository: CarRepository) : ViewModel() {

    private val _options = MutableLiveData<Options>()
    val options: LiveData<Options> = _options

    var defaultOptions = _options.value?.defaultOptions
    var selectOptions = _options.value?.selectOptions

    private val _nowOptionMode = MutableLiveData(OptionMode.SELECT_OPTION)
    val nowOptionMode: LiveData<OptionMode> = _nowOptionMode

    private val _selectedSelectOption = MutableLiveData<MutableList<Option>>(mutableListOf())
    val selectedSelectOption: LiveData<MutableList<Option>> = _selectedSelectOption

    private val _userTotalPrice = MutableLiveData(0)
    val userTotalPrice: LiveData<Int> = _userTotalPrice

    private var selectedDataFromDB: List<PriceData> = listOf()
    private var selectedOptionList: MutableList<PriceData> = mutableListOf()

    init {
        setOptionsData()
    }

    private fun setOptionsData() {
        viewModelScope.launch {
            val interior = repository.getTypeData(PriceDataType.INTERIOR_COLOR)
            _options.value = repository.getOptions(9, interior.code!!)
            defaultOptions = _options.value?.defaultOptions
            selectOptions = _options.value?.selectOptions
            getUserDataFromDB()
            _selectedSelectOption.value = selectOptions!!.filter { allData ->
                selectedDataFromDB.any { fromDB -> fromDB.code == allData.id }
            }.toMutableList()
        }
    }

    private fun changeOptionMode() {
        if (_nowOptionMode.value == OptionMode.SELECT_OPTION) {
            _nowOptionMode.value = OptionMode.DEFAULT_OPTION
        } else {
            _nowOptionMode.value = OptionMode.SELECT_OPTION
        }
    }

    fun addSelectedSelectOption(selectedOption: Option) {
        _selectedSelectOption.value?.add(selectedOption)
        _userTotalPrice.value = _userTotalPrice.value?.plus(selectedOption.price)
        selectedOptionList.add(
            PriceData(
                carId = 1,
                optionType = PriceDataType.OPTION,
                optionId = null,
                name = selectedOption.name,
                price = selectedOption.price,
                imgUrl = selectedOption.imageUrl,
                code = selectedOption.id
            )
        )
    }

    fun removeSelectedSelectOption(selectedOption: Option) {
        _selectedSelectOption.value?.remove(selectedOption)
        _userTotalPrice.value = _userTotalPrice.value?.minus(selectedOption.price)
        val selected = PriceData(
            carId = 1,
            optionType = PriceDataType.OPTION,
            optionId = null,
            name = selectedOption.name,
            price = selectedOption.price,
            imgUrl = selectedOption.imageUrl,
            code = selectedOption.id
        )
        if (selectedDataFromDB.contains(selected)) {
            val deleted = selectedDataFromDB.find { it == selected }
            viewModelScope.launch {
                repository.deleteOptionItem(deleted!!)
            }
        }
        selectedOptionList.remove(selected)
    }

    fun setNowOptionMode(selectedMode: OptionMode) {
        _nowOptionMode.value = selectedMode
    }

    fun setUserTotalPrice(price: Int) {
        _userTotalPrice.value = price
    }

    suspend fun saveUserSelection() {
        if (selectedOptionList.isNotEmpty()) {
            val indices = repository.setOptionTypeDataList(selectedOptionList)
            for (idx in selectedOptionList.indices) {
                selectedOptionList[idx].id = indices[idx].toInt()
            }
        }
    }

    suspend fun getUserDataFromDB() {
        selectedDataFromDB = repository.getOptionTypeDataList()
    }

    suspend fun updateCarData(){
        val old = repository.getMyCarData()
        val update = old!!.copy(
            totalPrice = userTotalPrice.value!!
        )
        repository.saveUserCarData(update)
    }
}