package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameReview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ConnectException


object GameReviewsRepository {
    suspend fun getOfflineReviews(context: Context) : List<GameReview>{
        return withContext(Dispatchers.IO){
            var db = AppDatabase.getInstance(context)
            var reviews = db.gameReviewDao().getOfflineDB()
            return@withContext reviews
        }
    }
    suspend fun sendOfflineReviews(context: Context) : Int{
        val gameReviews = getOfflineReviews(context)
        var count = 0
        val db = AppDatabase.getInstance(context)
        for(gameReview in gameReviews){
            try{
                sendReviewHelp(gameReview)
                db.gameReviewDao().setOnline(gameReview.igdb_id)
            }
            catch (e: Exception){
                continue
            }
            count++
        }
        return count
    }

    suspend fun sendReview(context: Context, gameReview: GameReview) : Boolean{
        var favoritesGames: List<Game> = mutableListOf()
        try {
            print("POKRENUT SEND REVIEW! \n" )
            sendReviewHelp(gameReview)
            favoritesGames = AccountGamesRepository.getSavedGames()
            if (favoritesGames.find { it.id == gameReview.igdb_id } == null) {
                    AccountGamesRepository.saveGame(GamesRepository.getGameById(gameReview.igdb_id))
                }
        }
        catch (e: Exception){
            return withContext(Dispatchers.IO){
                var db = AppDatabase.getInstance(context)
                gameReview.online = false
                db.gameReviewDao().insert(gameReview)
                return@withContext false
            }
        }


        return true
    }
    suspend fun sendReviewHelp(gameReview: GameReview) : GameReview {
        return withContext(Dispatchers.IO){
            val response = AccountApiConfig.retrofit.createGameReview(AccountGamesRepository.Account.acHash,gameReview , gameReview.igdb_id)
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
