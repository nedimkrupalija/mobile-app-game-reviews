package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameReview
import ba.etf.rma23.projekat.data.repositories.responses.SendGameReviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext




object GameReviewsRepository {
    suspend fun getOfflineReviews(context: Context) : List<GameReview>{
        return withContext(Dispatchers.IO){
            var db = AppDatabase.getInstance(context)
            var reviews = db.gameReviewDao().getOfflineDB()
            return@withContext reviews
        }
    }
    suspend fun sendOfflineReviews(context: Context) : Int{
        var gameReviews = getOfflineReviews(context)
        var count = 0
        var db = AppDatabase.getInstance(context)
        for(gameReview in gameReviews){
            if(sendReview(context, gameReview)){
                db.gameReviewDao().setOnline(gameReview.igdb_id)
                count++
            }
        }
        return count
    }

    suspend fun sendReview(context: Context, gameReview: GameReview) : Boolean{
        var favoritesGames = AccountGamesRepository.getSavedGames()
        try {
            sendReviewHelp(gameReview)
        }
        catch (e: Exception){
            return withContext(Dispatchers.IO){
                var db = AppDatabase.getInstance(context)
                gameReview.online = true
                db.gameReviewDao().insertAll(gameReview)
                return@withContext false
            }
        }
        if(favoritesGames.find { it.id == gameReview.igdb_id } == null){
            AccountGamesRepository.saveGame(GamesRepository.getGameById(gameReview.igdb_id))
        }

        return true
    }
    suspend fun sendReviewHelp(gameReview: GameReview) : GameReview {
        return withContext(Dispatchers.IO){
            val response = AccountApiConfig.retrofit.createGameReview(AccountGamesRepository.Account.acHash,SendGameReviewResponse(gameReview.rating, gameReview.review), gameReview.igdb_id)
            val responsebody = response.body()
            return@withContext responsebody!!
        }
    }
    suspend fun getReviewsForGame(igdb_id: Int) : List<GameReview> {
        return withContext(Dispatchers.IO){
            val response = AccountApiConfig.retrofit.getGameReviews(igdb_id)
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }
}