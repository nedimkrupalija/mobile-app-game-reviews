package ba.etf.rma23.projekat.data.repositories

import android.util.Log
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.UserImpression
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.Console
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round
import kotlin.time.Duration.Companion.days

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

object GamesRepository {

    suspend fun getGamesByName(name : String) : List<Game>?{
        val serializedList = getGamesByNameHelp(name)
        val gameList : MutableList<Game> = mutableListOf()
        var index: Int = 0

        var title: String
        var platform: String = ""
        var releaseDate: String
        var rating: Double
        var coverImage: String
        var esrbRating: String = ""
        var developer: String = ""
        var publisher: String = ""
        var genre: String = ""
        var description: String
        var userImpressions: List<UserImpression>?


        if (serializedList != null) {
            for(serializedGame in serializedList){
                esrbRating = "No rating"
                title = serializedGame.title.toString()
                if(serializedGame.platformList != null){
                    for(i in 0 until serializedGame.platformList.size){
                        platform = platform + serializedGame.platformList[i].name
                        if(i!=serializedGame.platformList.size-1) platform = "$platform, "
                    }
                }
                //platform = serializedGame.platformList.toString()
                if(serializedGame.ratingValue!=null){
                    var temp : Double= serializedGame.ratingValue/10.0
                    temp = temp.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
                    rating = temp
                }
                else rating = 0.0
                releaseDate = getDateFromEpoch(serializedGame.releaseDate)
                coverImage = serializedGame.cover?.url.toString()
                if(serializedGame.ESRBList!=null) {
                    for(esrb in serializedGame.ESRBList){
                        if(esrb.category==1){
                            esrbRating = ""
                            esrbRating += AgeRatingEnum.enumFromInt(esrb.rating)
                        }
                    }
                }
                if(serializedGame.companyList != null) {
                    for (company in serializedGame.companyList) {
                        val name = company.company.name

                        if(company.developer==true){
                            developer = "$developer$name, "
                        }
                        if(company.publisher==true){
                            publisher = "$publisher$name, "
                        }
                    }
                    if(publisher.last() == ' '){
                        publisher = publisher.dropLast(2)
                    }
                    if(developer.last() == ' '){
                        developer = developer.dropLast(2)
                    }
                }
                if(serializedGame.genreList != null){
                for(genres in serializedGame.genreList){
                        genre = genre + genres.genre + ", "
                    }
                    if(genre.last() == ' '){
                        genre = genre.dropLast(2)
                    }
                }

                description = serializedGame.description.toString()
                userImpressions = null
                gameList.add(index, Game(serializedGame.id, title,platform, releaseDate, rating, coverImage, esrbRating, developer, publisher, genre, description, userImpressions))
                index += 1
            }
        }
        return gameList
    }


    fun getGamesSafe(name : String):List<Game>{
        return listOf()
    }
    fun sortGames() : List<Game>{
        return listOf()
    }

    //Staviti upitnik
    private suspend fun getGamesByNameHelp(name : String):List<GameSerialized>?{

        return withContext(Dispatchers.IO){
            val response = IGDBApiConfig.retrofit.getGamesByName(name)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    private fun getDateFromEpoch(epoch : Long) : String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(epoch*1000L)
    }

}