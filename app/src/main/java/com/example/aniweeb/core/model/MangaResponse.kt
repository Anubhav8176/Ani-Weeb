package com.example.aniweeb.core.model

data class MangaResponse(
    val data: List<MangaMinInfo>
)

data class MangaMinInfo(
    val mal_id: Int,
    val title: String,
    val title_english: String,
    val score: Float,
    val volumes: Int?,
    val images: MangaImages,
    val status: String,
    val chapters: Int,
    val published: Aired,
    val rank: Int,
    val popularity: Int,
    val synopsis: String,
    val authors: List<Authors>,
    val external: List<External>
)

data class Authors(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
)

data class MangaFullInfo(
    val data: MangaMinInfo
)

data class MangaImages(
    val jpg: MangaImagesFormat,
    val webp: MangaImagesFormat
)

data class MangaImagesFormat(
    val image_url: String,
    val small_image_url: String,
    val large_image_url: String
)