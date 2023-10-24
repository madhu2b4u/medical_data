package com.demo.med.home.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.med.common.Event
import com.demo.med.common.PRESCRIPTION_LOADED
import com.demo.med.common.Result
import com.demo.med.common.SingleLiveEvent
import com.demo.med.common.SpUtil
import com.demo.med.common.Status
import com.demo.med.common.support.AppCoroutineDispatcherProvider
import com.demo.med.common.support.AppCoroutineDispatchers
import com.demo.med.database.entites.HealthData
import com.demo.med.home.domain.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mHomeUseCase: HomeUseCase
) : ViewModel(), LifecycleObserver {

    private val sharedPrefsHelpers = SpUtil.instance

    private val isPrescriptionLoaded = sharedPrefsHelpers?.getBoolean(PRESCRIPTION_LOADED, false)

    val data: LiveData<MutableList<HealthData>> get() = _data
    private val _data = MediatorLiveData<MutableList<HealthData>>()

    val healthData = MediatorLiveData<Event<Result<MutableList<HealthData>>>>()

    val showLoader: LiveData<Boolean> get() = _showLoader
    private val _showLoader = SingleLiveEvent<Boolean>()

    private val dispatcher: AppCoroutineDispatchers = AppCoroutineDispatcherProvider.dispatcher()

    init {
        isPrescriptionLoaded?.let { getProblemsList(it) }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getProblemsList(isLoaded: Boolean) {
        viewModelScope.launch {
            withContext(dispatcher.main()) {
                healthData.addSource(mHomeUseCase.drugsRequest(isLoaded)) {
                    healthData.value = Event(it)
                    handleDrugsResponse(it)
                }
            }
        }
    }

    private fun handleDrugsResponse(it: Result<MutableList<HealthData>>) {
        when (it.status) {
            Status.LOADING -> {
                _showLoader.value = true
            }

            Status.ERROR -> {
                _showLoader.value = false
            }

            Status.SUCCESS -> it.data?.let { it1 -> handleProblems(it.data) }
        }
    }

    private fun handleProblems(data: MutableList<HealthData>) {
        _showLoader.value = false
        sharedPrefsHelpers?.putBoolean(PRESCRIPTION_LOADED, true)
        _data.value = data
    }
}