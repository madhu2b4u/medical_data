package com.demo.med.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.med.LiveDataTestUtil
import com.demo.med.TestUtils
import com.demo.med.common.Status
import com.demo.med.database.source.LocalDataSource
import com.demo.med.home.data.remote.source.HomeRemoteDataSource
import com.demo.med.home.data.repository.HomeRepository
import com.demo.med.home.data.repository.HomeRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class HomeRepositoryTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repositoryTest: HomeRepository

    @Mock
    lateinit var dataStore: HomeRemoteDataSource

    @Mock
    lateinit var localStore: LocalDataSource

    private val data = TestUtils.healthDataList
    private val drugResponse = TestUtils.response

    private val dispatcher = StandardTestDispatcher()


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repositoryTest = HomeRepositoryImpl(dataStore, localStore)
    }

    @Test
    fun getDrugsFromAPI() {
        CoroutineScope(dispatcher).launch {
            Mockito.`when`(dataStore.drugsRequest())
                .thenReturn(drugResponse)

            val result = repositoryTest.drugsRequest(false)
            assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
            assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)

            assert(LiveDataTestUtil.getValue(result).data == data)
        }
    }

    @Test
    fun getDrugsFromLocalStore() {
        CoroutineScope(dispatcher).launch {
            Mockito.`when`(localStore.geHealthData())
                .thenReturn(data)

            val result = repositoryTest.drugsRequest(true)
            assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
            assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)

            assert(LiveDataTestUtil.getValue(result).data == data)
        }
    }

    @Test(expected = Exception::class)
    fun getDrugsFromAPIThrowsException() = TestScope().runTest {
        Mockito.doThrow(Exception("no drugs"))
            .`when`(dataStore.drugsRequest())
        repositoryTest.drugsRequest(false)
    }

}