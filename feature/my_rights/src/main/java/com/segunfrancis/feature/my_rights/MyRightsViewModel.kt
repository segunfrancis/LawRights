package com.segunfrancis.feature.my_rights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.segunfrancis.data.remote.repository.LawRightsPagingSource
import com.segunfrancis.data.remote.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class MyRightsViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    lawRightsPagingSource: LawRightsPagingSource
) :
    ViewModel() {

    fun updateTime() {
        remoteRepository.addLastUpdated(System.currentTimeMillis())
    }

     val myRights = Pager(
         config = PagingConfig(pageSize = 10)
     ) {
         lawRightsPagingSource
     }.flow.cachedIn(viewModelScope)
}
