package ru.synergy.data.mappers

import ru.synergy.data.entities.BookEntity
import ru.synergy.domain.entities.Volume
import ru.synergy.domain.entities.VolumeInfo

class BookEntityMapper {
    fun toBookEntity(volume: Volume): BookEntity {
        return BookEntity(
            id = volume.id,
            title = volume.volumeInfo.title,
            authors = volume.volumeInfo.authors,
            imageUrl = volume.volumeInfo.imageUrl
        )
    }

    fun toVolume(bookEntity: BookEntity): Volume {
        return Volume(
            bookEntity.id,
            VolumeInfo(bookEntity.title, bookEntity.authors, bookEntity.imageUrl)
        )
    }
}