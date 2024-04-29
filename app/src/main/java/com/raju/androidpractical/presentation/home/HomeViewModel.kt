// HomeViewModel.kt

package com.raju.androidpractical.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _pan = MutableLiveData<String>()
    val pan: LiveData<String> = _pan

    private val _day = MutableLiveData<String>()
    val day: LiveData<String> = _day

    private val _month = MutableLiveData<String>()
    val month: LiveData<String> = _month

    private val _year = MutableLiveData<String>()
    val year: LiveData<String> = _year

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    init {
        _isButtonEnabled.value = false
    }

    fun updatePan(panValue: String) {
        _pan.value = panValue
        validateFields()
    }

    fun updateDay(dayValue: String) {
        _day.value = dayValue
        validateFields()
    }

    fun updateMonth(monthValue: String) {
        _month.value = monthValue
        validateFields()
    }

    fun updateYear(yearValue: String) {
        _year.value = yearValue
        Log.e("year",_year.value .toString())
        validateFields()
    }

    private fun validateFields() {
        val isPanValid = _pan.value?.isNotEmpty() ?: false && _pan.value?.length == 10
        val isDayValid = isValidDay(_day.value.orEmpty(), _month.value.orEmpty(), _year.value.orEmpty())
        val isMonthValid = _month.value?.toIntOrNull() in 1..12
        val isYearValid = _year.value?.toIntOrNull() in 1900..2100

        _isButtonEnabled.value = isPanValid && isDayValid && isMonthValid && isYearValid
        Log.e("isPanValid",""+isPanValid)
        Log.e("isDayValid",""+isDayValid)
        Log.e("isMonthValid",""+isMonthValid)
        Log.e("isYearValid",""+isYearValid)
    }

    private fun isValidDay(day: String, month: String, year: String): Boolean {
        val monthInt = month.toIntOrNull() ?: return false
        val yearInt = year.toIntOrNull() ?: return false
        val dayInt = day.toIntOrNull() ?: return false

        if (monthInt !in 1..12) return false

        // Checking for February
        if (monthInt == 2) {
            // Checking for leap year
            if (yearInt % 4 == 0 && (yearInt % 100 != 0 || yearInt % 400 == 0)) {
                return dayInt in 1..29
            } else {
                return dayInt in 1..28
            }
        }

        // Checking for months with 30 days
        if (monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt == 11) {
            return dayInt in 1..30
        }

        // For all other months with 31 days
        return dayInt in 1..31
    }
}
