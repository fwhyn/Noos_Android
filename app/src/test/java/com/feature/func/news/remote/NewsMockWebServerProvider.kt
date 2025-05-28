package com.feature.func.news.remote

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class NewsMockWebServerProvider {

    companion object {
        // TODO move to dependency injection, based on fake or real build
        const val DUMMY_API_KEY = "asdf8aw30nk"

        const val OK = "ok"
        const val TOTAL_RESULT = 5
        const val STATUS_ERROR = "error"
        const val API_KEY_MISSING = "apiKeyMissing"
        const val API_KEY_INVALID = "apiKeyInvalid"
        const val PARAMETERS_MISSING = "parametersMissing"
        const val NOT_FOUND = "notFound"
    }

    private val successResponse = """
        {
            "status": "$OK",
            "totalResults": $TOTAL_RESULT,
            "articles": [
                {
                    "source": {
                        "id": null,
                        "name": "Gizmodo.com"
                    },
                    "author": "Lucas Ropek",
                    "title": "19-Year-Old to Plead Guilty to Hacking Charges",
                    "description": "A company with the personal information of tens of millions of children was breached last year.",
                    "url": "https://gizmodo.com/19-year-old-to-plead-guilty-to-hacking-charges-after-data-breach-of-millions-of-schoolchildren-2000605540",
                    "urlToImage": "https://gizmodo.com/app/uploads/2022/03/17144a5c640c20e7a04ffa123ce0fd2a.jpg",
                    "publishedAt": "2025-05-21T18:53:36Z",
                    "content": "A Massachusetts teenager has pled guilty to a number of hacking crimes, including his role in the penetration of a cloud company with data on tens of millions of children, the government says.\r\nOn We… [+2678 chars]"
                },
                {
                    "source": {
                        "id": null,
                        "name": "Slashdot.org"
                    },
                    "author": "msmash",
                    "title": "Pakistan Allocates 2,000 Megawatts of Electricity To Bitcoin Mining, AI Data Centres",
                    "description": "Pakistan will allocate 2,000 megawatts (MW) of electricity in the first phase of a national initiative to power bitcoin mining and AI data centres, its finance ministry said on Sunday. The allocation is part of Islamabad's plans to use its surplus electricity…",
                    "url": "https://slashdot.org/story/25/05/26/1549251/pakistan-allocates-2000-megawatts-of-electricity-to-bitcoin-mining-ai-data-centres",
                    "urlToImage": "https://a.fsdn.com/sd/topics/bitcoin_64.png",
                    "publishedAt": "2025-05-26T17:00:00Z",
                    "content": "The bomb will never go off. I speak as an expert in explosives.\r\n-- Admiral William Leahy, U.S. Atomic Bomb Project"
                },
                {
                    "source": {
                        "id": null,
                        "name": "Yahoo Entertainment"
                    },
                    "author": "Brian McGleenon",
                    "title": "Bitcoin price holds above ${'$'}102,000 as BlackRock leads fund inflows",
                    "description": "Bitcoin traded relatively flat on Thursday as institutional investors resumed allocations into US-based spot bitcoin exchange-traded funds on Wednesday.",
                    "url": "https://uk.finance.yahoo.com/news/bitcoin-price-blackrock-fund-inflows-083952678.html",
                    "urlToImage": "https://s.yimg.com/ny/api/res/1.2/VccZ29AP5EbMUUA1Bt5b8A--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD03Njk-/https://s.yimg.com/os/creatr-uploaded-images/2024-03/1a844b70-e77d-11ee-afed-082fc5f5ad1b",
                    "publishedAt": "2025-05-15T08:39:52Z",
                    "content": "Bitcoin (BTC-USD) traded relatively flat on Thursday, priced just above ${'$'}102,200 (£76,989) down around 1% on the day as institutional investors resumed allocations into US-based spot bitcoin exchange… [+2465 chars]"
                },
                {
                    "source": {
                        "id": "business-insider",
                        "name": "Business Insider"
                    },
                    "author": "Reed Alexander",
                    "title": "Jamie Dimon opens the door to bitcoin, warns against stagflation in wide-ranging remarks to investors",
                    "description": "The JPMorgan CEO said the bank will allow investors to buy bitcoin while warning against stagflation and sounding a hopeful note on regulations.",
                    "url": "https://www.businessinsider.com/jamie-dimon-bitcoin-stagflation-regulation-trum-investor-day-remarks-2025-5",
                    "urlToImage": "https://i.insider.com/682b87e3c6ad288d1481573e?width=1200&format=jpeg",
                    "publishedAt": "2025-05-19T19:35:06Z",
                    "content": "Jamie DimonTom Williams/CQ-Roll Call, Inc via Getty Images\r\n<ul><li>JPMorgan CEO Jamie Dimon addressed a range of topics at the firm's Investor Day meeting on Monday.</li><li>He said the bank will al… [+3395 chars]"
                },
                {
                    "source": {
                        "id": null,
                        "name": "Yahoo Entertainment"
                    },
                    "author": "Murtuza J Merchant",
                    "title": "El Salvador's Bitcoin Holdings Show ${'$'}357 Million In Unrealized Profit As Bitcoin Closes At Record Highs",
                    "description": "El Salvador's bold foray into Bitcoin (CRYPTO: BTC) has entered a new chapter of profitability. As of May 19, the country's BTC portfolio stands at...",
                    "url": "https://finance.yahoo.com/news/el-salvadors-bitcoin-holdings-show-033134288.html",
                    "urlToImage": "https://media.zenfs.com/en/benzinga_79/adbae4051492d8a88035da448858fe44",
                    "publishedAt": "2025-05-20T03:31:34Z",
                    "content": "Benzinga and Yahoo Finance LLC may earn commission or revenue on some items through the links below.\r\nEl Salvador's bold foray into Bitcoin (CRYPTO: BTC) has entered a new chapter of profitability.\r\n… [+1958 chars]"
                }
            ]
        }
        """.trimIndent()

    private val missingKeyResponse = """
        {
            "status": "$STATUS_ERROR",
            "code": "$API_KEY_MISSING",
            "message": "Your API key is missing. Append this to the URL with the apiKey param, or use the x-api-key HTTP header."
        }
    """.trimIndent()

    private val invalidKeyResponse = """
        {
            "status": "$STATUS_ERROR",
            "code": "$API_KEY_INVALID",
            "message": "Your API key is invalid or incorrect. Check your key, or go to https://newsapi.org to create a free API key."
        }
    """.trimIndent()

    private val missingParamsResponse = """
        {
            "status": "$STATUS_ERROR",
            "code": "$PARAMETERS_MISSING",
            "message": "Required parameters are missing. Please check the documentation."
        }
    """.trimIndent()

    private val notFoundResponse = """
        {
            "status": "$STATUS_ERROR",
            "code": "$NOT_FOUND",
            "message": "Not found."
        }
    """.trimIndent()

    fun get(): MockWebServer {
        val mockWebServer = MockWebServer()

        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {

                val authHeader = request.getHeader("Authorization")
                val apiKeyQueryParam = request.requestUrl?.queryParameter("apiKey")

                if (authHeader == null && apiKeyQueryParam == null) {
                    return MockResponse()
                        .setResponseCode(401)
                        .setBody(missingKeyResponse)
                }

                // Check API key (either header or query param)
                val validToken = "Bearer $DUMMY_API_KEY"
                val authorized = (authHeader == validToken) || (apiKeyQueryParam == DUMMY_API_KEY)
                if (!authorized) {
                    return MockResponse()
                        .setResponseCode(401)
                        .setBody(invalidKeyResponse)
                }

                // Simulate endpoint not found
                if (request.path?.startsWith("/everything") != true) {
                    return MockResponse().setResponseCode(404).setBody(notFoundResponse)
                }

                // Simulate missing required params (e.g., q=bitcoin or sources=abc-news)
                val queryParams = request.requestUrl?.queryParameterNames ?: emptySet()
                if (!queryParams.contains("q") && !queryParams.contains("sources")) {
                    return MockResponse()
                        .setResponseCode(400)
                        .setBody(missingParamsResponse)
                }

                // Successful response
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(successResponse)
            }
        }

        return mockWebServer
    }
}