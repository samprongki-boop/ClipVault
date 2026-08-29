package com.clipvault.app.data

import kotlinx.coroutines.flow.Flow

class ClipRepository(private val dao: ClipDao) {

    val allClips: Flow<List<ClipEntity>> = dao.getAllClips()

    fun searchClips(query: String): Flow<List<ClipEntity>> = dao.searchClips(query)
    fun getClipsByCategory(category: String): Flow<List<ClipEntity>> = dao.getClipsByCategory(category)
    fun getFavoriteClips(): Flow<List<ClipEntity>> = dao.getFavoriteClips()

    suspend fun saveClip(text: String, sourcePackage: String? = null): Long {
        if (text.isBlank()) return -1L

        val existing = dao.findByText(text.trim())
        if (existing != null) {
            dao.update(
                existing.copy(
                    updatedAt = System.currentTimeMillis(),
                    copyCount = existing.copyCount + 1,
                    sourcePackage = sourcePackage ?: existing.sourcePackage
                )
            )
            return existing.id
        }

        val category = detectCategory(text)
        val clip = ClipEntity(
            text = text.trim(),
            category = category,
            sourcePackage = sourcePackage
        )
        return dao.insert(clip)
    }

    suspend fun getRecentClips(limit: Int = 20): List<ClipEntity> = dao.getRecentClips(limit)
    suspend fun updateClip(clip: ClipEntity) = dao.update(clip)
    suspend fun deleteClip(clip: ClipEntity) = dao.delete(clip)
    suspend fun deleteById(id: Long) = dao.deleteById(id)

    suspend fun togglePin(id: Long, pinned: Boolean) =
        dao.setPinned(id, pinned, System.currentTimeMillis())

    suspend fun toggleFavorite(id: Long, favorite: Boolean) =
        dao.setFavorite(id, favorite, System.currentTimeMillis())

    suspend fun incrementCopyCount(id: Long) = dao.incrementCopyCount(id)
    suspend fun clearAll() = dao.clearNonPinnedNonFavorite()
    suspend fun getAllForBackup(): List<ClipEntity> = dao.getAllForBackup()
    suspend fun restoreFromBackup(clips: List<ClipEntity>) = dao.insertAll(clips)
    suspend fun getCount(): Int = dao.getCount()

    private fun detectCategory(text: String): String {
        val urlRegex = Regex("https?://[\\w\\-]+(\\.[\\w\\-]+)+[/#?]?.*$")
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val phoneRegex = Regex("^[+]?[\\d\\s\\-()]{8,}$")
        val codeRegex = Regex("(fun |def |class |public |private |const |var |import |#include|function )")

        return when {
            urlRegex.containsMatchIn(text) -> "url"
            emailRegex.containsMatchIn(text) -> "email"
            phoneRegex.matches(text.trim()) -> "phone"
            codeRegex.containsMatchIn(text) -> "code"
            else -> "general"
        }
    }
}