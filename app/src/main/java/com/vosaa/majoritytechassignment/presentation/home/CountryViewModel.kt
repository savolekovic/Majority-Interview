package com.vosaa.majoritytechassignment.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vosaa.majoritytechassignment.domain.models.Country
import com.vosaa.majoritytechassignment.domain.repository.CountryRepository
import com.vosaa.majoritytechassignment.util.UiStates
import com.vosaa.majoritytechassignment.util.error.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository,
) : ViewModel() {

    private val _countriesDataFlow = MutableSharedFlow<List<Country>>()
    val countriesDataFlow = _countriesDataFlow.asSharedFlow()

    private val _eventFlow = MutableSharedFlow<UiStates?>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val handler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            _eventFlow.emit(
                when (exception) {
                    is NoInternetException -> UiStates.NO_INTERNET_CONNECTION
                    else -> {
                        Timber.d("Exception: ${exception.localizedMessage}")
                        UiStates.UNKNOWN_ERROR
                    }
                }
            )
        }
    }

    fun getAllCountries() {
        viewModelScope.launch(handler) {
            _eventFlow.emit(UiStates.LOADING)
            repository.getAllCountries().collect {
                _countriesDataFlow.emit(it)
                _eventFlow.emit(UiStates.SUCCESS)
            }
        }
    }

    fun refreshCountries() {
        viewModelScope.launch(handler) {
            _eventFlow.emit(UiStates.LOADING)
            repository.refreshCountries().collect {
                _countriesDataFlow.emit(it)
                _eventFlow.emit(UiStates.SUCCESS)
            }
        }
    }

}