package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object AccountGamesRepository {
    fun setHash(acHash: String): Boolean{
        return true
    }
    fun getHash(): String{
        return ""
    }
    suspend fun getSavedGames():List<Game>{
        val responses = getUserGamesReponse()
        val gameList: MutableList<Game> = mutableListOf()
        if (responses != null) {
            for(response in responses){
                gameList.addAll(GamesRepository.getGamesByName(response.gameTitle)!!)
                for(game in gameList){
                    if(game.id!=response.igdbId) gameList.remove(game)
                }
            }
        }
    return gameList
    }
    // Promijeniti upitnik
    fun saveGame() : Game?{
        return null
    }
     suspend fun removeGame(id: Int): Boolean{
         val rez = removeGameHelp(id)
         print("REZULTATFJ: " + rez!!.message + "\n")
        return rez.message!="null"
    }
    fun removeNonSafe():Boolean{
        return true
    }
    suspend fun getGamesContainingString(query:String):List<Game>{
        val response = getUserGamesReponse()
        return GamesRepository.getGamesByName(query)!!
    }
    fun setAge(age:Int):Boolean{
        return true
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

}