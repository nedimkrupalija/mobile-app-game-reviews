package ba.etf.rma23.projekat.data.repositories.responses

import com.google.gson.annotations.SerializedName

data class AgeRating(
    @SerializedName("id") val id : Int,
    @SerializedName("category") val category : Int,
    @SerializedName("rating") val rating : Int
)