package com.segunfrancis.data.remote.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.segunfrancis.data.remote.model.DataX

@Dao
interface LawRightsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rights: List<DataX>?)

    @Query("SELECT * FROM rights_table")
    fun pagingSource(): PagingSource<Int, DataX>

    @Query("DELETE FROM rights_table")
    suspend fun clearAll()
}
