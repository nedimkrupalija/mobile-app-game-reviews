package ba.etf.rma23.projekat.data.repositories

import androidx.compose.ui.text.toLowerCase
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.Account.favoriteGames
import ba.etf.rma23.projekat.data.repositories.responses.AccountGameResponse
import ba.etf.rma23.projekat.data.repositories.responses.DeletedGameResponse
import ba.etf.rma23.projekat.data.repositories.responses.GameBodyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountGamesRepository {

    object  Account{
        val student: String = ""
        var acHash: String = "da694fdf-cd2e-4da6-b80d-e1a81e41bd25"
        var age: Int = 99
        var favoriteGames: MutableList<Game> = mutableListOf()
        fun gamesWithString(query: String) : List<Game>{
            val returnGames : MutableList<Game> = mutableListOf()
            for(game in favoriteGames){
                if(game.title.lowercase().contains(query.lowercase()))
                    returnGames.add(game)
            }
            return returnGames
        }

        fun isInLocalGames(id : Int) : Boolean{
            for(game in favoriteGames){
                if(game.id == id){
                    return true
                }
            }
            return false
        }

        fun getLocalById(id : Int) : Game?{
            for(game in favoriteGames){
                if(game.id == id)
                    return game
            }
            return null
        }

    }

    fun setHash(acHash: String): Boolean{
        Account.acHash = acHash
        return true
    }
    fun getHash(): String{
        return Account.acHash
    }

    fun getSavedGamesLocal() : List<Game>{
        return Account.favoriteGames
    }

    suspend fun getSavedGames():List<Game>{
        val responses : MutableList<AccountGameResponse> = getUserGamesReponse() as MutableList<AccountGameResponse>
        favoriteGames = mutableListOf()
        for(response in responses){
            favoriteGames.add(GamesRepository.getGameById(response.igdbId))
        }
    return favoriteGames
    }

    suspend fun saveGame(game: Game): Game? {
        val gameHelp = saveHelp(game)
            return if (gameHelp != null) {
                val game = GamesRepository.getGameById(gameHelp.igdbId)
                favoriteGames.add(game)
                game
        } else null


    }
     suspend fun removeGame(id: Int): Boolean{
         favoriteGames.remove(Account.getLocalById(id))
         removeGameHelp(id)

        return true
    }
    suspend fun removeNonSafe():Boolean{
        for(game in Account.favoriteGames){
            if(!checkGameRating(game)) {
                removeGame(game.id)

            }
        }
        favoriteGames = getSavedGames() as MutableList<Game>
        //Update games
        return true
    }
    fun removeNonSafeLocal() : List<Game>{
        val temp = favoriteGames

        temp.removeAll{!checkGameRating(it) }
        return temp
    }

      suspend fun getGamesContainingString(query:String):List<Game>{
         favoriteGames = getSavedGames() as MutableList<Game>
        return Account.gamesWithString(query)
    }

    fun getGamesContainingStringLocal(query: String) : List<Game>{
        return Account.gamesWithString(query)
    }
    fun setAge(age:Int):Boolean{
        if(age in 3..100){
            Account.age = age
            return true
        }
        return false
    }


    private suspend fun getUserGamesReponse() : List<AccountGameResponse>?{
        return withContext(Dispatchers.IO){
            val response = AccountApiConfig.retrofit.getUserGames(Account.acHash)
            val responseBody = response.body()

            return@withContext responseBody
        }
    }

    private suspend fun removeGameHelp(id: Int): DeletedGameResponse? {
        return withContext(Dispatchers.IO){
            val response = AccountApiConfig.retrofit.removeGame(Account.acHash,id)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    fun checkGameRating(game: Game) : Boolean{
        when(game.esrbRating){
            "RP" -> return true
            "E" -> return true
            "E10" -> return Account.age >= 10
            "T" -> return Account.age >= 13
            "M" -> return Account.age >= 17
            "AO" -> return Account.age >= 18
            "Three" -> return Account.age >= 3
            "Seven" -> return Account.age >= 7
            "Sixteen" -> return Account.age >= 16
            "Eighteen" -> return Account.age >= 18

        }
        return true
    }

    private suspend fun saveHelp(game : Game) : AccountGameResponse?{
        return withContext(Dispatchers.IO){

               val responseBody = AccountApiConfig.retrofit.saveGame(
                   Account.acHash,
                   GameBodyResponse(AccountGameResponse(game.id, game.title))
               ).body()!!

        return@withContext responseBody
        }
    }


}