package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class InvolvedCompany(
    @SerializedName("id") val id: Int,
    @SerializedName("company") val company : Company,
    @SerializedName("developer") val developer : Boolean,
    @SerializedName("publisher") val publisher : Boolean
)
