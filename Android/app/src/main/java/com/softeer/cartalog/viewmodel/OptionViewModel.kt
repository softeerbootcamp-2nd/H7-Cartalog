package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.enums.OptionMode
import com.softeer.cartalog.data.model.Option
import com.softeer.cartalog.data.model.Options
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class OptionViewModel(private val repository: CarRepository) : ViewModel() {
    private val _options = MutableLiveData<Options>()
    val options: LiveData<Options> = _options

    var defaultOptions = _options.value?.defaultOptions
    var selectOptions = _options.value?.selectOptions

    private val _nowOptionMode = MutableLiveData(OptionMode.SELECT_OPTION)
    val nowOptionMode: LiveData<OptionMode> = _nowOptionMode

    private val _selectedDefaultOption = MutableLiveData(0)
    val selectedDefaultOption: LiveData<Int> = _selectedDefaultOption

    private val _selectedSelectOption = MutableLiveData(0)
    val selectedSelectOption: LiveData<Int> = _selectedSelectOption

    init {
        setOptionsData()
    }

    private fun setOptionsData() {
        viewModelScope.launch {
            // TODO : exteriorColorCode 부분 추후 RoomDB에서 불러온값으로 초기화 해야함
            _options.value = repository.getOptions(9, "I50")
            defaultOptions = _options.value?.defaultOptions
            selectOptions = _options.value?.selectOptions
        }
    }

//    private fun setOptionsData(): Options {
//        // 임시 데이터 설정
//        return Options(
//            listOf("상세품목", "악세사리"),
//            listOf(
//                Option(
//                    11,
//                    "컴포트 II",
//                    "상세품목",
//                    "시트",
//                    "",
//                    350000,
//                    38,
//                    listOf("장거리운전")
//                ),
//                Option(
//                    11,
//                    "듀얼 와이드 선루프",
//                    null,
//                    "시트",
//                    "",
//                    350000,
//                    38,
//                    listOf("장거리운전")
//                ),
//                Option(
//                    11,
//                    "빌트인 캠",
//                    null,
//                    "시트",
//                    "",
//                    350000,
//                    38,
//                    listOf("장거리운전")
//                ),
//                Option(
//                    11,
//                    "주차보조 시스템 II",
//                    null,
//                    "시트",
//                    "",
//                    350000,
//                    38,
//                    listOf("장거리운전")
//                )
//            ),
//            listOf(
//                Option(
//                    11,
//                    "디젤 2.2",
//                    "상세품목",
//                    "시트",
//                    "",
//                    350000,
//                    38,
//                    listOf("장거리운전")
//                )
//            )
//        )
//    }

    private fun changeOptionMode() {
        if (_nowOptionMode.value == OptionMode.SELECT_OPTION) {
            _nowOptionMode.value = OptionMode.DEFAULT_OPTION
        } else {
            _nowOptionMode.value = OptionMode.SELECT_OPTION
        }
    }

    fun setSelectedDefaultOption(selectedOption: Int){
        _selectedDefaultOption.value = selectedOption
    }

    fun setSelectedSelectOption(selectedOption: Int){
        _selectedSelectOption.value = selectedOption
    }

    fun setNowOptionMode(selectedMode: OptionMode){
        _nowOptionMode.value = selectedMode
    }
}