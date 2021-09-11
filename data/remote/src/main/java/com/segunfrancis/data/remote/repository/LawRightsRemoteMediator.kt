package com.segunfrancis.data.remote.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.segunfrancis.data.remote.api.LawRightsApi
import com.segunfrancis.data.remote.db.LawRightsDatabase
import com.segunfrancis.data.remote.model.DataX
import com.segunfrancis.data.remote.model.RemoteKeys
import com.segunfrancis.data.remote.util.RemoteConstants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class LawRightsRemoteMediator @Inject constructor(
    private val repository: RemoteRepository,
    private val database: LawRightsDatabase,
    private val api: LawRightsApi
) : RemoteMediator<Int, DataX>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, DataX>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        database.remoteKeysDao().remoteKeysRepoId(state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.id)
                    }
                    if (remoteKey?.nextKey == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.nextKey
                    /*val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    if (nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    nextKey*/
                }
            }
            val response = api.getAllRights(page = loadKey, token = repository.getToken())
            val rights = response.data.data
            val endOfPagination = rights.isEmpty()
            database.withTransaction {
                // clear all tables in db
                if (loadType == LoadType.REFRESH) {
                    database.dao().clearAll()
                    database.remoteKeysDao().clearRemoteKeys()
                }
                val prevKey = if (loadKey == STARTING_PAGE_INDEX) null else loadKey?.minus(1)
                val nextKey = if (endOfPagination) null else loadKey?.plus(1)
                val keys = rights.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.dao().insertAll(rights)
            }

            MediatorResult.Success(endOfPaginationReached = response.data.current_page != response.data.last_page)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

   /* override suspend fun initialize(): InitializeAction {
        *//*val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
        return if (System.currentTimeMillis() - repository.getLastUpdated() >= cacheTimeout) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }*//*
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }*/

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DataX>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { data ->
                // Get the remote keys of the last item retrieved
                database.remoteKeysDao().remoteKeysRepoId(data.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, DataX>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().remoteKeysRepoId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DataX>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { data ->
                // Get the remote keys of the first items retrieved
                database.remoteKeysDao().remoteKeysRepoId(data.id)
            }
    }
}
