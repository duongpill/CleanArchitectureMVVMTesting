package com.duongnh.catastrophic.domain.use_case

import com.duongnh.catastrophic.data.data_source.remote.dto.CatRequest
import com.duongnh.catastrophic.data.repository.FakeCatRepository
import com.duongnh.catastrophic.domain.MyResult
import com.duongnh.catastrophic.domain.repository.CatRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCatsUseCaseTest {

    private lateinit var catRepository: CatRepository
    private lateinit var getCatsUseCase: GetCatsUseCase

    @Before
    fun setUp() {
        catRepository = FakeCatRepository()
        getCatsUseCase = GetCatsUseCase(catRepository)
    }

    @Test
    fun `Get Cats, success`() = runBlocking {
        getCatsUseCase.invoke(CatRequest(10, 1, "png"), false).collect { result ->
            assertThat(result).isInstanceOf(MyResult.Success::class.java)
            if (result is MyResult.Success) {
                val cats = result.data
                assertThat(cats).isNotEmpty()
            }
        }
    }

    @Test
    fun `Get Cats, error`() = runBlocking {
        getCatsUseCase.invoke(CatRequest(10, 1, "png"), false).collect { result ->
            assertThat(result).isInstanceOf(MyResult.Error::class.java)
            if (result is MyResult.Error) {
                val messageError = result.rawResponse
                assertThat(messageError).isEqualTo("Fail")
            }
        }
    }

}