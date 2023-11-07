package com.example.playlistmaker1.media.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.playlistmaker1.media.data.database.dao.PlaylistDao
import com.example.playlistmaker1.media.data.database.dao.SelectedTracksDao
import com.example.playlistmaker1.media.data.database.entity.PlaylistEntity
import com.example.playlistmaker1.media.data.database.entity.TrackEntity
import com.example.playlistmaker1.media.data.database.entity.TypeConverter

@Database(version = 6, entities = [TrackEntity::class, PlaylistEntity::class])
@TypeConverters(TypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun selectedTracksDao(): SelectedTracksDao
    abstract fun playlistsDao(): PlaylistDao
}