package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.UserImpression
import ba.etf.rma23.projekat.data.repositories.responses.AgeRatingEnum
import ba.etf.rma23.projekat.data.repositories.responses.GameSerialized
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.reflect.Type

import java.math.RoundingMode
import java.text.SimpleDateFormat

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}


object GamesRepository {
    var gamesRepositoryList : MutableList<Game> = mutableListOf()
    suspend fun getGameById(id : Int) : Game {
        val serializedGame = getGameByIdHelp(id)
        return makeGameFromSerialized(serializedGame[0])
    }

    suspend fun getGamesByName(name : String) : List<Game>{
        val serializedList = getGamesByNameHelp(name)
        gamesRepositoryList = mutableListOf()
        if (serializedList != null) {
            for(serializedGame in serializedList){
                gamesRepositoryList.add(makeGameFromSerialized((serializedGame)))
            }
        }

        return gamesRepositoryList
    }


      suspend fun getGamesSafe(name : String):List<Game>{
          gamesRepositoryList = getGamesByName(name) as MutableList<Game>
          gamesRepositoryList.removeAll{!AccountGamesRepository.checkGameRating(it)}
            return gamesRepositoryList
    }



    fun sortGames() : List<Game>{
        AccountGamesRepository.Account.favoriteGames.sortBy { it.title }

        val intersectedGames = AccountGamesRepository.Account.favoriteGames.intersect(
            gamesRepositoryList.toSet()
        )

        gamesRepositoryList.sortBy { it.title }
        for(game in intersectedGames){
            gamesRepositoryList.add(0,game)
        }
        gamesRepositoryList = gamesRepositoryList.distinct() as MutableList<Game>
        return gamesRepositoryList
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

    private suspend fun getGameByIdHelp(id : Int) : List<GameSerialized> {

         return withContext(Dispatchers.IO) {
             val client = OkHttpClient()
             val mediaType = "text/plain".toMediaType()
             val body =
                 "fields id,age_ratings.category,age_ratings.rating,rating,cover.url,first_release_date,genres.name,involved_companies.company.name, involved_companies.publisher, involved_companies.developer, name,platforms.name,summary; where id = ${id};".toRequestBody(
                     mediaType
                 )
             val request = Request.Builder()
                 .url("https://api.igdb.com/v4/games")
                 .post(body)
                 .addHeader("Client-ID", "8xedbwl0e7vku8o54ysapymuhk041h")
                 .addHeader("Authorization", "Bearer eg3q4odwsas8h1dv52ozch590wea9t")
                 .addHeader("Content-Type", "text/plain")
                 .build()
             val response = client.newCall(request).execute()
             val gson: Gson = Gson()
             val type = object : TypeToken<List<GameSerialized>>() {}.type
            val result: List<GameSerialized> = parseArray<List<GameSerialized>>(json=response.body!!.string(), typeToken = type)
            return@withContext result

         }
    }

    private inline fun <reified T> parseArray(json: String, typeToken: Type): T {
        val gson = GsonBuilder().create()
        return gson.fromJson<T>(json, typeToken)
    }

    private fun makeGameFromSerialized(serializedGame : GameSerialized) : Game{
        var index: Int = 0

        var title: String
        var platform: String = "Unknown"
        var releaseDate: String
        var rating: Double
        var coverImage: String
        var esrbRating: String = ""
        var developer: String = ""
        var publisher: String = ""
        var genre: String = "Unknown"
        var description: String = "No description"
        var userImpressions: List<UserImpression>?

        esrbRating = "No rating"
        title = serializedGame.title.toString()
        if(serializedGame.platformList != null){
            platform = ""
            for(i in 0 until serializedGame.platformList.size){
                platform = platform + serializedGame.platformList[i].name
                if(i!=serializedGame.platformList.size-1) platform = "$platform, "
            }
        }

        if(serializedGame.ratingValue!=null){
            var temp : Double= serializedGame.ratingValue/10.0
            temp = temp.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
            rating = temp
        }
        else rating = 0.0
        releaseDate = if(serializedGame.releaseDate!=0L) {
            getDateFromEpoch(serializedGame.releaseDate)
        } else{
            "Unknown release date"
        }
        coverImage = serializedGame.cover?.url.toString()
        if(serializedGame.ESRBList!=null) {
            for(esrb in serializedGame.ESRBList){
                if(esrb.category==1){
                    esrbRating = ""
                    esrbRating += AgeRatingEnum.enumFromInt(esrb.rating)
                }
                else if(esrb.category==2){
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
            if(publisher.isNotEmpty() && publisher.last() == ' '){
                publisher = publisher.dropLast(2)
            }
            if(developer.isNotEmpty() && developer.last() == ' '){
                developer = developer.dropLast(2)
            }
            if(developer == "") {
                developer = "Unknown developer"
            }
            if(publisher==""){
                publisher = "Unknown publisher"
            }
        }
        if(serializedGame.genreList != null){
            genre = ""
            for(genres in serializedGame.genreList){
                genre = genre + genres.genre + ", "
            }
            if(genre.last() == ' '){
                genre = genre.dropLast(2)
            }
        }
        if(serializedGame.description!=null) {
            description = serializedGame.description.toString()
        }
        userImpressions = null


        return Game(serializedGame.id, title,platform, releaseDate, rating, coverImage, esrbRating, developer, publisher, genre, description, userImpressions)
    }





}