package ba.etf.rma23.projekat.data.repositories.responses

import com.google.gson.annotations.SerializedName

data class SendGameReviewResponse(
    @SerializedName("rating") var rating: Int?,
    @SerializedName("review") var review: String?
)