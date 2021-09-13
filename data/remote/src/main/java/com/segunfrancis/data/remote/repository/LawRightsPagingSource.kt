package com.segunfrancis.data.remote.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.segunfrancis.data.remote.api.LawRightsApi
import com.segunfrancis.data.remote.model.DataX
import javax.inject.Inject

class LawRightsPagingSource @Inject constructor(
    private val api: LawRightsApi,
    private val repository: RemoteRepository
) :
    PagingSource<Int, DataX>() {

    override fun getRefreshKey(state: PagingState<Int, DataX>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
        return try {
            val nextPage = params.key ?: 1
            val response = api.getAllRights(nextPage,  repository.getToken())
            LoadResult.Page(
                data = response.data.data,
                prevKey = null,
                nextKey = if (response.data.current_page < response.data.last_page) response.data.current_page + 1 else null
            )
        } catch (e: Throwable) {
            Log.e("LawRightsPagingSource", e.localizedMessage)
            LoadResult.Error(e)
        }
    }
}
