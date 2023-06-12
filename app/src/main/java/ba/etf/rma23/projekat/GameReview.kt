package ba.etf.rma23.projekat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GameReview(
    @PrimaryKey(true) var id: Int,
    @ColumnInfo(name = "rating") @SerializedName("rating") var rating: Int?,
    @ColumnInfo(name = "igdb_id") @SerializedName("GameId") var igdb_id: Int,
    @ColumnInfo(name = "review") @SerializedName("review") var review: String?,
    @ColumnInfo(name = "online") var online: Boolean,
    @SerializedName("timestamp") var timestamp: String
    )
