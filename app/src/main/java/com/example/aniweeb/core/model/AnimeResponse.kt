package com.example.aniweeb.core.model


data class AnimeResponse(
    val data: List<AnimeMinInfo>
)

data class AnimeFullInfo(
    val data: AnimeMinInfo
)

data class AnimeMinInfo(
    val mal_id: Int,
    val title: String,
    val title_english: String,
    val score: Float,
    val episodes: Int,
    val images: AnimeImages,
    val type: String,
    val status: String,
    val trailer: Trailer,
    val source: String,
    val aired: Aired,
    val duration: String,
    val rating: String,
    val popularity: Int,
    val rank: Int,
    val season: String,
    val year: Int,
    val external: List<External>,
    val synopsis: String
)

data class External(
    val name: String,
    val url: String
)

data class Aired(
    val from: String,
    val to: String?,
    val prop: Prop,
    val string: String
)

data class Prop(
    val from: Dates,
    val to: Dates
)

data class Dates(
    val day: String?,
    val month: String?,
    val year: String?
)

data class Trailer(
    val youtube_id: String,
    val url: String
)

data class AnimeImages(
    val jpg: AnimeImagesFormat,
    val webp: AnimeImagesFormat
)

data class AnimeImagesFormat(
    val image_url: String,
    val small_image_url: String,
    val large_image_url: String
)