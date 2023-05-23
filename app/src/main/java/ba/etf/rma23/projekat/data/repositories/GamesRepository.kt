package ba.etf.rma23.projekat.data.repositories

import android.util.Log
import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.Console

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

object GamesRepository {
    //Staviti upitnik
    suspend fun getGamesByName(name : String):List<GameSerialized>?{

        return withContext(Dispatchers.IO){
            val response = IGDBApiConfig.retrofit.getGamesByName()
            val responseBody = response.body()
            print(responseBody.toString())

            return@withContext responseBody
        }
    }
    fun getGamesSafe(name : String):List<Game>{
        return listOf()
    }
    fun sortGames() : List<Game>{
        return listOf()
    }


}