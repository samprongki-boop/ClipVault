package com.clipvault.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clips")
data class ClipEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val category: String = "general",
    val isPinned: Boolean = false,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val copyCount: Int = 0,
    val sourcePackage: String? = null
)

data class ClipBackup(
    val clips: List<ClipEntity>,
    val version: Int = 1,
    val exportDate: Long = System.currentTimeMillis()
)