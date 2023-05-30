package ba.etf.rma23.projekat.data.repositories.responses

import com.google.gson.annotations.SerializedName

data class DeletedGameResponse(
    @SerializedName("success") val message: String
)
