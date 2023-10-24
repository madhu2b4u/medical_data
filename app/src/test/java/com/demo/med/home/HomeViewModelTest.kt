package com.demo.med.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.demo.med.LiveDataTestUtil
import com.demo.med.TestUtils
import com.demo.med.common.Result
import com.demo.med.common.Status
import com.demo.med.database.entites.HealthData
import com.demo.med.home.domain.HomeUseCase
import com.demo.med.home.presentation.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: HomeUseCase

    private lateinit var viewModel: HomeViewModel

    private val data = TestUtils.healthDataList

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun init() {
        useCase = mock()
    }

    @Test
    fun testGetProblemsListLoadingData() {

        CoroutineScope(dispatcher).launch {
            useCase = mock {
                onBlocking { drugsRequest(false) } doReturn liveData {
                    emit(Result.loading())
                }
            }
            viewModel = HomeViewModel(useCase)
            viewModel.getProblemsList(false)
            val result = viewModel.healthData
            result.observeForever { }
            delay(2000)
            assert(LiveDataTestUtil.getValue(result).peekContent().status == Status.LOADING)
        }
    }

    @Test
    fun testGetProblemsListSuccessData() {
        CoroutineScope(dispatcher).launch {
            useCase = mock {
                onBlocking { drugsRequest(false) } doReturn liveData {
                    emit(Result.success(data))
                }
            }

            viewModel = HomeViewModel(useCase)
            viewModel.getProblemsList(false)

            val result = viewModel.healthData
            result.observeForever {}
            delay(2000)
            assert(
                LiveDataTestUtil.getValue(result).peekContent().status == Status.SUCCESS &&
                        LiveDataTestUtil.getValue(result).peekContent().data == data
            )
        }

    }

    @Test
    fun testGetProblemsListErrorData() {

        CoroutineScope(dispatcher).launch {
            useCase = mock {
                onBlocking { drugsRequest(false) } doReturn object :
                    LiveData<Result<MutableList<HealthData>>>() {
                    init {
                        value = Result.error("error")
                    }
                }
            }

            viewModel = HomeViewModel(useCase)
            viewModel.getProblemsList(false)

            val result = viewModel.healthData
            result.observeForever {}
            delay(2000)
            assert(
                LiveDataTestUtil.getValue(result).peekContent().status == Status.ERROR &&
                        LiveDataTestUtil.getValue(result).peekContent().message == "error"
            )

        }

    }

}