package ru.synergy.domain.usecases

import ru.synergy.domain.repositories.BooksRepository

class GetBooksUseCase(private val booksRepository: BooksRepository) {
    suspend operator fun invoke(author: String) = booksRepository.getRemoteBooks(author)
}