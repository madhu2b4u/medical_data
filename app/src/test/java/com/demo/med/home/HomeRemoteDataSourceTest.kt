package com.demo.med.home

import com.demo.med.TestUtils
import com.demo.med.home.data.remote.services.HomeService
import com.demo.med.home.data.remote.source.HomeRemoteDataSource
import com.demo.med.home.data.remote.source.HomeRemoteDataSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.Response

@ExperimentalCoroutinesApi
class HomeRemoteDataSourceTest {


    lateinit var service: HomeService

    lateinit var dataSource: HomeRemoteDataSource

    @Test
    fun getDrugs() = runBlocking {

        service = mock {
            onBlocking {
                requestDrugsAsync()
            } doReturn GlobalScope.async {
                Response.success(TestUtils.response)
            }
        }

        dataSource = HomeRemoteDataSourceImpl(service, coroutineContext)

        val result = dataSource.drugsRequest()

        assert(result == TestUtils.response)
    }

    @Test(expected = Exception::class)
    fun getDrugsThrowException() = runBlocking {
        service = mock {
            onBlocking {
                requestDrugsAsync()
            } doReturn GlobalScope.async {
                throw Exception()
            }
        }

        dataSource = HomeRemoteDataSourceImpl(service, coroutineContext)

        val result = dataSource.drugsRequest()

        assert(result == TestUtils.response)
    }


}