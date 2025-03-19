package com.example.supers.data

import androidx.annotation.ColorRes
import com.example.supers.R
import com.google.gson.annotations.SerializedName

class SuperheroResponse(
    val response: String ,
    val results: List<Superhero>
)


 data class Superhero (
    val id: String ,
    val name: String ,
    val biography: Biography,
    val work: Work,
    val appearance: Appearance,
    val powerstats : Powerstats,
    val image : Image
)
 {
     //@ColorRes
     fun getAlignmentColor(): Int{
         return when(biography.alignment){
             "good" -> R.color.alignment_good
             "bad"-> R.color.alignment_bad
             else -> R.color.alignment_neutral

         }
     }
 }

 data class Biography (
    val publisher: String ,
    @SerializedName("full-name") val fullName: String ,
    @SerializedName("place-of-birth") val placeBirth: String ,
    val alignment: String ,
    @SerializedName("alter-egos") val alterEgos: String
)

 data class Work (
    val occupation: String ,
    val base: String
)

 data class Appearance (
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

 data class Powerstats (
    val intelligence: String ,
    val strength: String ,
    val speed: String ,
    val durability: String ,
    val power: String ,
    val combat: String
)

class Image (val url: String)