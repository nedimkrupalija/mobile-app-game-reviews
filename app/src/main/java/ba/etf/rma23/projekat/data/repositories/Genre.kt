package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class Genre (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val genre : String
    )