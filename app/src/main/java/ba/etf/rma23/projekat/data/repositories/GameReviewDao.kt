package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma23.projekat.GameReview

@Dao
interface GameReviewDao {
    @Query("SELECT * FROM gamereview")
    suspend fun getAll() : List<GameReview>
    @Insert
    suspend fun insertAll(vararg gameReviews: GameReview)

    @Query("SELECT * FROM gamereview WHERE online=0")
    suspend fun getOfflineDB() : List<GameReview>

    @Query("SELECT COUNT(*) FROM gamereview where online=0")
    suspend fun offlineCount() : Int

    @Query("UPDATE gamereview set online=1 where igdb_id=")
    suspend fun setOnline(): Void

}