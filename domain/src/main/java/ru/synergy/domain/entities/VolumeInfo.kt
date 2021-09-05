package ru.synergy.domain.entities

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val imageUrl: String?
)