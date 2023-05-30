package ba.etf.rma23.projekat.data.repositories.responses

import com.google.gson.annotations.SerializedName

data class InvolvedCompany(
    @SerializedName("id") val id: Int,
    @SerializedName("company") val company : Company,
    @SerializedName("developer") val developer : Boolean,
    @SerializedName("publisher") val publisher : Boolean
)
