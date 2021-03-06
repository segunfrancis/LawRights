package com.segunfrancis.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rights_table")
data class DataX(
    @PrimaryKey
    val id: Int,
    val is_deleted: Int,
    val title: String,
    val updated_at: String
)
