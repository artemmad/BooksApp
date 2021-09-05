package ru.synergy.domain.usecases

import ru.synergy.domain.entities.Volume
import ru.synergy.domain.repositories.BooksRepository

class BookmarkBookUseCase(private val booksRepository: BooksRepository) {
    suspend operator fun invoke(book: Volume) = booksRepository.bookmark(book)
}