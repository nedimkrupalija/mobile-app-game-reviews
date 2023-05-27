package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object AccountGamesRepository {
    fun setHash(acHash: String): Boolean{
        Account.acHash = acHash
        return true
    }
    fun getHash(): String{
        return Account.acHash
    }
    suspend fun getSavedGames():List<Game>{
        val responses : MutableList<AccountGameResponse> = getUserGamesReponse() as MutableList<AccountGameResponse>
        val gameList: MutableList<Game> = mutableListOf()
        for(response in responses){
            gameList.add(GamesRepository.getGameById(response.igdbId))
        }
    return gameList
    }
    // Promijeniti upitnik
    suspend fun saveGame(game: Game): Boolean {
       /* */
        var check : Boolean = false
         withContext(Dispatchers.IO){
             val favoriteGames : MutableList<Game> = getSavedGames() as MutableList<Game>
             print("SAVED GAMES: " + favoriteGames.toString() + "\n")
             if(!favoriteGames.contains(game)) {
                 AccountApiConfig.retrofit.saveGame(
                     "da694fdf-cd2e-4da6-b80d-e1a81e41bd25",
                     GameBodyResponse(AccountGameResponse(game.id, game.title))
                 )
                 check = true
             }
        }
        return check
    }
     suspend fun removeGame(id: Int): Boolean{
         removeGameHelp(id)
         Account.favoriteGames = getSavedGames()
        return true
    }
    suspend fun removeNonSafe():Boolean{
        for(game in Account.favoriteGames){
            if(!checkGameRating(game)) removeGame(game.id)
        }
        //Update games
        Account.favoriteGames = getSavedGames()
        return true
    }
     suspend fun getGamesContainingString(query:String):List<Game>{
        val response = getUserGamesReponse()
        return GamesRepository.getGamesByName(query)!!
         //Mozda implementacija
        //return Account.gamesWithString(query)
    }
    fun setAge(age:Int):Boolean{
        if(age in 3..100){
            Account.age = age
            return true
        }
        return false
    }
    suspend fun login() : String? {
        return withContext(Dispatchers.IO){
            val response = AccountApiConfig.retrofit.login()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    private suspend fun getUserGamesReponse() : List<AccountGameResponse>?{
        return withContext(Dispatchers.IO){
            val response = AccountApiConfig.retrofit.getUserGames("da694fdf-cd2e-4da6-b80d-e1a81e41bd25")
            val responseBody = response.body()

            return@withContext responseBody
        }
    }

    private suspend fun removeGameHelp(id: Int): DeletedGameResponse? {
        return withContext(Dispatchers.IO){
            val response = AccountApiConfig.retrofit.removeGame("da694fdf-cd2e-4da6-b80d-e1a81e41bd25",id)
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
        }
        return true
    }

}