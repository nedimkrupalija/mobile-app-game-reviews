package ba.etf.rma23.projekat.data.repositories.responses

import com.google.gson.annotations.SerializedName

data class AccountGameResponse(
    //@SerializedName("id") val id: Int,
    @SerializedName("igdb_id") val igdbId: Int,
    @SerializedName("name") val gameTitle: String

)
