package com.demo.med.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import com.demo.med.LiveDataTestUtil
import com.demo.med.TestUtils
import com.demo.med.common.Result
import com.demo.med.common.Status
import com.demo.med.home.data.repository.HomeRepository
import com.demo.med.home.domain.HomeUseCase
import com.demo.med.home.domain.HomeUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class HomeUseCaseTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var useCase: HomeUseCase

    lateinit var repository: HomeRepository

    private val data = TestUtils.healthDataList

    private val dispatcher = StandardTestDispatcher()


    @Test
    fun testDrugsRequestLoading() {

        CoroutineScope(dispatcher).launch {
            repository = mock {
                onBlocking {
                    drugsRequest(false)
                } doReturn liveData {
                    emit(Result.loading())
                }
            }

            useCase = HomeUseCaseImpl(repository)

            val result = useCase.drugsRequest(false)

            assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        }

    }

    @Test
    fun testGDrugsRequestSuccess() {

        CoroutineScope(dispatcher).launch {
            repository = mock {
                onBlocking {
                    drugsRequest(false)
                } doReturn liveData {
                    emit(Result.success(data))
                }
            }

            useCase = HomeUseCaseImpl(repository)

            val result = useCase.drugsRequest(false)

            assert(
                LiveDataTestUtil.getValue(result).data == data &&
                        LiveDataTestUtil.getValue(result).status == Status.SUCCESS
            )
        }


    }

    @Test
    fun testDrugsRequestErrorData() {

        CoroutineScope(dispatcher).launch {
            repository = mock {
                onBlocking { drugsRequest(false) } doReturn liveData {
                    emit(Result.error("no drugs"))
                }
            }
            useCase = HomeUseCaseImpl(repository)
            val result = useCase.drugsRequest(false)

            result.observeForever { }
            assert(
                LiveDataTestUtil.getValue(result).status == Status.ERROR && LiveDataTestUtil.getValue(
                    result
                ).message == "no drugs"
            )
        }
    }

}