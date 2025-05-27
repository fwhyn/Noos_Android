package com.fwhyn.app.noos.feature.func.news.data.remote

import com.fwhyn.app.noos.feature.func.news.data.model.Article
import com.fwhyn.app.noos.feature.func.news.data.model.GetArticlesRepoParam

class ArticlesRemoteDataSourceImpl(
    private val newsApi: NewsApi,
) : ArticlesRemoteDataSource {
    override suspend fun get(param: GetArticlesRepoParam): List<Article> {
        val response = newsApi.searchEverything(

        )

        return response.articles
    }
}