package com.feature.func.news.remote

import MainDispatcherRule
import com.fwhyn.app.noos.feature.func.news.data.model.NewsResponse
import com.fwhyn.app.noos.feature.func.news.data.remote.NewsApi
import com.fwhyn.app.noos.feature.func.news.di.NewsModule
import com.fwhyn.app.noos.feature.func.news.di.RetrofitForNewsModule
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

class NewsApiTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val retrofitForNewsModule = RetrofitForNewsModule()
    private val newsModule = NewsModule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var newsApi: NewsApi

    @Before
    fun setUp() {
        mockWebServer = NewsMockWebServerProvider().get()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should return error if api key is missing`() = runTest {
        initialize(null)

        var response: NewsResponse? = null
        try {
            response = newsApi.searchEverything("bitcoin")
        } catch (e: Throwable) {
            val error = e as? HttpException
            val errorString = error?.response()?.errorBody()?.string() ?: ""
            val result: NewsResponse = Gson().fromJson(errorString, NewsResponse::class.java)

            response = result
        } finally {
            Assert.assertEquals(NewsMockWebServerProvider.STATUS_ERROR, response?.status)
            Assert.assertEquals(NewsMockWebServerProvider.API_KEY_MISSING, response?.code)
        }
    }

    @Test
    fun `should return error if api key is invalid`() = runTest {
        initialize { NewsMockWebServerProvider.DUMMY_API_KEY + "no" }

        var response: NewsResponse? = null
        try {
            response = newsApi.searchEverything("bitcoin")
        } catch (e: Throwable) {
            val error = e as? HttpException
            val errorString = error?.response()?.errorBody()?.string() ?: ""
            val result: NewsResponse = Gson().fromJson(errorString, NewsResponse::class.java)

            response = result
        } finally {
            Assert.assertEquals(NewsMockWebServerProvider.STATUS_ERROR, response?.status)
            Assert.assertEquals(NewsMockWebServerProvider.API_KEY_INVALID, response?.code)
        }
    }

    @Test
    fun `should return the correct data when searchEverything(query) is called`() = runTest {
        initialize()

        val response: NewsResponse = newsApi.searchEverything(query = "bitcoin")

        Assert.assertEquals(NewsMockWebServerProvider.OK, response.status)
        Assert.assertEquals(NewsMockWebServerProvider.TOTAL_RESULT, response.total)
        Assert.assertEquals(NewsMockWebServerProvider.TOTAL_RESULT, response.articles.size)
        Assert.assertEquals("Gizmodo.com", response.articles[0].source.name)
        Assert.assertEquals("Lucas Ropek", response.articles[0].author)
        Assert.assertEquals("19-Year-Old to Plead Guilty to Hacking Charges", response.articles[0].title)
    }

    fun initialize() {
        initialize { NewsMockWebServerProvider.DUMMY_API_KEY }
    }

    fun initialize(onGetKey: (() -> String)?) {
        val retrofit = retrofitForNewsModule.provideRetrofit(mockWebServer.url("/"), onGetKey)
        newsApi = newsModule.provideNewsApi(retrofit)
    }
}

