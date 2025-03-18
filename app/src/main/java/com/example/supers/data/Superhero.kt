package com.example.supers.data

import com.google.gson.annotations.SerializedName

class SuperheroResponse(
    val response: String ,
    val results: List<Superhero>
)


class Superhero (
    val id: String ,
    val name: String ,
    val biography: Biography,
    val work: Work,
    val appearance: Appearance,
    val powerstats : Powerstats ,
    val image : Image
)

class Biography (
    val publisher: String ,
    @SerializedName("full-name") val fullName: String ,
    @SerializedName("place-of-birth") val placeBirth: String ,
    val alignment: String ,
    @SerializedName("alter-egos") val alterEgos: String
)

class Work (
    val occupation: String ,
    val base: String
)

class Appearance (
    val gender: String ,
    val race: String ,
    val height: List<String>,
    val weight: List<String>,
)
{
    fun getHeightCm(): String {
    return height[1]
}
    fun getWeightKg(): String {
        return weight[1]
    }
}

class Powerstats (
    val intelligence: String ,
    val strenght: String ,
    val speed: String ,
    val durability: String ,
    val power: String ,
    val combat: String ,
)

class Image (val url: String)