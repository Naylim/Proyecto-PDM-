package com.example.segundoexamen.data

data class TVShow(
    val id: Int,
    val name: String,
    val genres: List<String>,
    val rating: Rating,
    val image: Image
)

data class Rating(
    val average: Double?
)

data class Image(
    val medium: String,
    val original: String
)