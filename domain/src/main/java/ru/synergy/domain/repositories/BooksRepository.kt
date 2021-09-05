package ru.synergy.domain.repositories

import ru.synergy.domain.common.Result
import ru.synergy.domain.entities.Volume
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    suspend fun getRemoteBooks(author: String): Result<List<Volume>>

    suspend fun getBookmarks(): Flow<List<Volume>>

    suspend fun bookmark(book: Volume)

    suspend fun unbookmark(book: Volume)
}