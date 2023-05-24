package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.UserImpression
import com.google.gson.annotations.SerializedName

data class GameSerialized(
    @SerializedName("id") val id : Int,
    @SerializedName("age_ratings") val ESRBList: List<AgeRating>?,
    @SerializedName("rating") val ratingValue: Double?,
    @SerializedName("cover") val cover : Cover?,
    @SerializedName("first_release_date") val releaseDate: Long,
    @SerializedName("genres") val genreList: List<Genre>?,
    @SerializedName("involved_companies") val companyList: List<InvolvedCompany>?,
    @SerializedName("name") val title: String?,
    @SerializedName("platforms") val platformList: List<Platform>?,
    @SerializedName("summary") val description: String?
)
