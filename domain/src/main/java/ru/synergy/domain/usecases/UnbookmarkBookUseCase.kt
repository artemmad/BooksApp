package ru.synergy.domain.usecases

import ru.synergy.domain.entities.Volume
import ru.synergy.domain.repositories.BooksRepository

class UnbookmarkBookUseCase(private val booksRepository: BooksRepository) {
    suspend operator fun invoke(book: Volume) = booksRepository.unbookmark(book)
}