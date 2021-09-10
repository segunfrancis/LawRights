package com.segunfrancis.feature.my_rights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.segunfrancis.data.remote.repository.LawRightsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRightsViewModel @Inject constructor(private val pagingSource: LawRightsPagingSource) :
    ViewModel() {

    val myRights = Pager(
        PagingConfig(pageSize = 10)
    ) {
        pagingSource
    }.flow
        .cachedIn(viewModelScope)
}
