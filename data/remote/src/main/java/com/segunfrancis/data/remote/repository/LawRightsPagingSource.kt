package com.segunfrancis.data.remote.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.segunfrancis.data.remote.api.LawRightsApi
import com.segunfrancis.data.remote.model.DataX
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class LawRightsPagingSource @Inject constructor(
    private val api: LawRightsApi,
    private val token: String?
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
            val response = api.getAllRights(nextPage, token ?: "")
            LoadResult.Page(
                data = response.data.data,
                prevKey = null,
                nextKey = if (response.data.current_page < response.data.last_page) response.data.current_page + 1 else null
            )
        } catch (e: IOException) {
            // IOException for network failures.
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            LoadResult.Error(e)
        }
    }
}