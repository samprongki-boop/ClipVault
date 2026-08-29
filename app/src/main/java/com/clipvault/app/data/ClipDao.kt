package com.clipvault.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClipDao {

    @Query("SELECT * FROM clips ORDER BY isPinned DESC, updatedAt DESC")
    fun getAllClips(): Flow<List<ClipEntity>>

    @Query("SELECT * FROM clips WHERE text LIKE '%' || :query || '%' ORDER BY isPinned DESC, updatedAt DESC")
    fun searchClips(query: String): Flow<List<ClipEntity>>

    @Query("SELECT * FROM clips WHERE category = :category ORDER BY isPinned DESC, updatedAt DESC")
    fun getClipsByCategory(category: String): Flow<List<ClipEntity>>

    @Query("SELECT * FROM clips WHERE isFavorite = 1 ORDER BY isPinned DESC, updatedAt DESC")
    fun getFavoriteClips(): Flow<List<ClipEntity>>

    @Query("SELECT * FROM clips WHERE text = :text LIMIT 1")
    suspend fun findByText(text: String): ClipEntity?

    @Insert
    suspend fun insert(clip: ClipEntity): Long

    @Update
    suspend fun update(clip: ClipEntity)

    @Delete
    suspend fun delete(clip: ClipEntity)

    @Query("DELETE FROM clips WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM clips WHERE isPinned = 0 AND isFavorite = 0")
    suspend fun clearNonPinnedNonFavorite()

    @Query("UPDATE clips SET isPinned = :pinned, updatedAt = :time WHERE id = :id")
    suspend fun setPinned(id: Long, pinned: Boolean, time: Long)

    @Query("UPDATE clips SET isFavorite = :favorite, updatedAt = :time WHERE id = :id")
    suspend fun setFavorite(id: Long, favorite: Boolean, time: Long)

    @Query("UPDATE clips SET copyCount = copyCount + 1 WHERE id = :id")
    suspend fun incrementCopyCount(id: Long)

    @Query("SELECT COUNT(*) FROM clips")
    suspend fun getCount(): Int

    @Query("SELECT * FROM clips ORDER BY updatedAt DESC LIMIT :limit")
    suspend fun getRecentClips(limit: Int): List<ClipEntity>

    @Query("SELECT * FROM clips")
    suspend fun getAllForBackup(): List<ClipEntity>

    @Insert
    suspend fun insertAll(clips: List<ClipEntity>)
}