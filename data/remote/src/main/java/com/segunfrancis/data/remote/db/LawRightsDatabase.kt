package com.segunfrancis.data.remote.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.segunfrancis.data.remote.model.DataX
import com.segunfrancis.data.remote.model.RemoteKeys

@Database(entities = [DataX::class, RemoteKeys::class], version = 1, exportSchema = true)
abstract class LawRightsDatabase : RoomDatabase() {

    abstract fun dao(): LawRightsDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
