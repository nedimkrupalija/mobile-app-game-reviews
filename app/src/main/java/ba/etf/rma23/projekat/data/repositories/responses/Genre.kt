package ba.etf.rma23.projekat.data.repositories.responses

import com.google.gson.annotations.SerializedName

data class Genre (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val genre : String
    )