package ru.synergy.data.repositories.books

import kotlinx.coroutines.flow.Flow
import ru.synergy.data.repositories.books.BooksLocalDataSource
import ru.synergy.data.repositories.books.BooksRemoteDataSource
import ru.synergy.domain.common.Result
import ru.synergy.domain.entities.Volume
import ru.synergy.domain.repositories.BooksRepository

class BooksRepositoryImpl(
    private val localDataSource: BooksLocalDataSource,
    private val remoteDataSource: BooksRemoteDataSource
) : BooksRepository {

    override suspend fun getRemoteBooks(author: String): Result<List<Volume>> {
        return remoteDataSource.getBooks(author)
    }

    override suspend fun getBookmarks(): Flow<List<Volume>> {
        return localDataSource.getBookmarks()
    }

    override suspend fun bookmark(book: Volume) {
        localDataSource.bookmark(book)
    }

    override suspend fun unbookmark(book: Volume) {
        localDataSource.unbookmark(book)
    }
}