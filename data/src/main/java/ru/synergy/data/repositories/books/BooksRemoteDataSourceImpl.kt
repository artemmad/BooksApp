package ru.synergy.data.repositories.books

import ru.synergy.data.api.BooksApi
import ru.synergy.data.mappers.BookApiResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.synergy.data.repositories.books.BooksRemoteDataSource
import ru.synergy.domain.entities.Volume
import ru.synergy.domain.common.Result

class BooksRemoteDataSourceImpl(
    private val service: BooksApi,
    private val mapper: BookApiResponseMapper
) : BooksRemoteDataSource {
    override suspend fun getBooks(author: String): Result<List<Volume>> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getBooks(author)
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toVolumeList(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}