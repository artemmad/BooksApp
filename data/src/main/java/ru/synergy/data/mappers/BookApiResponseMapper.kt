package ru.synergy.data.mappers

import ru.synergy.data.api.BooksApiResponse
import ru.synergy.domain.entities.Volume
import ru.synergy.domain.entities.VolumeInfo


class BookApiResponseMapper {
    fun toVolumeList(response: BooksApiResponse): List<Volume> {
        return response.items.map {
            Volume(
                it.id, VolumeInfo(
                    it.volumeInfo.title,
                    it.volumeInfo.authors,
                    it.volumeInfo.imageLinks?.thumbnail?.replace("http", "https")
                )
            )
        }
    }
}