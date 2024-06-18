package com.example.segundoexamen.data.detail

import com.example.segundoexamen.data.Image
import com.example.segundoexamen.data.Rating

data class TVShowDetail(
    val id: Int,
    val name: String,
    val rating: Rating,
    val genres: List<String>,
    val premiered: String?,
    val network: Network?,
    val language: String?,
    val summary: String?,
    val image: Image,
    val _embedded: Embedded?
)

data class Network(
    val name: String?
)

data class Embedded(
    val cast: List<CastMember>
)

data class CastMember(
    val person: Person
)

data class Person(
    val name: String
)
