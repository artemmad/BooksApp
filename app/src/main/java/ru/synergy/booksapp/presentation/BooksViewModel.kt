package ru.synergy.booksapp.presentation

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import ru.synergy.booksapp.entities.BookWithStatus
import ru.synergy.booksapp.mappers.BookWithStatusMapper
import kotlinx.coroutines.launch
import ru.synergy.domain.entities.Volume
import ru.synergy.domain.usecases.BookmarkBookUseCase
import ru.synergy.domain.usecases.GetBookmarksUseCase
import ru.synergy.domain.usecases.GetBooksUseCase
import ru.synergy.domain.usecases.UnbookmarkBookUseCase
import ru.synergy.domain.common.Result


class BooksViewModel(
    private val getBooksUseCase: GetBooksUseCase,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val bookmarkBookUseCase: BookmarkBookUseCase,
    private val unbookmarkBookUseCase: UnbookmarkBookUseCase,
    private val mapper: BookWithStatusMapper
) : ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _books = MutableLiveData<List<BookWithStatus>>()
    val books = _books

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _remoteBooks = arrayListOf<Volume>()

    // Getting books with uncle bob as default author :)
    fun getBooks(author: String) {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            when (val booksResult = getBooksUseCase.invoke(author)) {
                is Result.Success -> {
                    _remoteBooks.clear()
                    _remoteBooks.addAll(booksResult.data)

                    val bookmarksFlow = getBookmarksUseCase.invoke()
                    bookmarksFlow.collect { bookmarks ->
                        books.value = mapper.fromVolumeToBookWithStatus(_remoteBooks, bookmarks)
                        _dataLoading.postValue(false)
                    }
                }

                is Result.Error -> {
                    _dataLoading.postValue(false)
                    books.value = emptyList()
                    _error.postValue(booksResult.exception.message)
                }
            }
        }
    }

    fun bookmark(book: BookWithStatus) {
        viewModelScope.launch {
            bookmarkBookUseCase.invoke(mapper.fromBookWithStatusToVolume(book))
        }
    }

    fun unbookmark(book: BookWithStatus) {
        viewModelScope.launch {
            unbookmarkBookUseCase.invoke(mapper.fromBookWithStatusToVolume(book))
        }
    }

    class BooksViewModelFactory(
        private val getBooksUseCase: GetBooksUseCase,
        private val getBookmarksUseCase: GetBookmarksUseCase,
        private val bookmarkBookUseCase: BookmarkBookUseCase,
        private val unbookmarkBookUseCase: UnbookmarkBookUseCase,
        private val mapper: BookWithStatusMapper
    ) :
        ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BooksViewModel(
                getBooksUseCase,
                getBookmarksUseCase,
                bookmarkBookUseCase,
                unbookmarkBookUseCase,
                mapper
            ) as T
        }
    }
}