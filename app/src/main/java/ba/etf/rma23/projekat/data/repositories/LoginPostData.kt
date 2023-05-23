package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class LoginPostData(
    @SerializedName("Client-ID") var clientId: String,
    @SerializedName("Authorization") var password: String
)
