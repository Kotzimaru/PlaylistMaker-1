package com.example.playlistmaker1.media.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.playlistmaker1.media.data.database.dao.SelectedTracksDao
import com.example.playlistmaker1.media.data.database.entity.TrackEntity
import com.example.playlistmaker1.media.data.database.entity.TypeConverter

@Database(version = 1, entities = [TrackEntity::class])
@TypeConverters(TypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    
    abstract fun dao(): SelectedTracksDao
}