package com.example.supers.data

class SuperheroResponse(
    val response: String ,
    val results: List<Superhero>
)


class Superhero (
    val id: String ,
    val name: String ,
    val biography: Biography,
    val image : Image
) {
}

class Biography (
    val publisher: String
)

class Image (val url: String)