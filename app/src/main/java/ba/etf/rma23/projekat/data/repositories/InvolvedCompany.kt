package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class InvolvedCompany(
    @SerializedName("id") val id: Int,
    val name : Company
)
