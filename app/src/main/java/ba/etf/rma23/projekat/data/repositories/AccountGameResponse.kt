package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class AccountGameResponse(
    //@SerializedName("id") val id: Int,
    @SerializedName("igdb_id") val igdbId: Int,
    @SerializedName("name") val gameTitle: String

)
