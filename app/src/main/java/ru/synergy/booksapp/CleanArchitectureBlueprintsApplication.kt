package ru.synergy.booksapp

import android.app.Application
import ru.synergy.booksapp.di.ServiceLocator
import ru.synergy.booksapp.mappers.BookWithStatusMapper
import ru.synergy.data.repositories.books.BooksRepositoryImpl
import ru.synergy.domain.usecases.BookmarkBookUseCase
import ru.synergy.domain.usecases.GetBookmarksUseCase
import ru.synergy.domain.usecases.GetBooksUseCase
import ru.synergy.domain.usecases.UnbookmarkBookUseCase

import timber.log.Timber

class CleanArchitectureBlueprintsApplication : Application() {
    private val booksRepository: BooksRepositoryImpl
        get() = ServiceLocator.provideBooksRepository(this)

    val getBooksUseCase: GetBooksUseCase
        get() = GetBooksUseCase(booksRepository)

    val getBookmarksUseCase: GetBookmarksUseCase
        get() = GetBookmarksUseCase(booksRepository)

    val bookmarkBooksUseCase: BookmarkBookUseCase
        get() = BookmarkBookUseCase(booksRepository)

    val unbookmarkBookUseCase: UnbookmarkBookUseCase
        get() = UnbookmarkBookUseCase(booksRepository)

    val bookWithStatusMapper = BookWithStatusMapper()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}