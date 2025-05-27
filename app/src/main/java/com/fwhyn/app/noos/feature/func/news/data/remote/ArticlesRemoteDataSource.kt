package com.fwhyn.app.noos.feature.func.news.data.remote

import com.fwhyn.app.noos.feature.func.news.data.model.Article
import com.fwhyn.app.noos.feature.func.news.data.model.GetArticlesRepoParam
import com.fwhyn.lib.baze.common.data.BaseGetterCoroutine

interface ArticlesRemoteDataSource : BaseGetterCoroutine<GetArticlesRepoParam, List<Article>>