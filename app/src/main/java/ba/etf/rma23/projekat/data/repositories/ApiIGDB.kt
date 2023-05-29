package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.BuildConfig
import ba.etf.rma23.projekat.Game
import org.junit.runners.Parameterized.Parameter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

const val p: String = "abc"
public const val fieldsString : String = "id, age_ratings.category, age_ratings.rating, rating, cover.url, first_release_date, genres.name, involved_companies.company.name, involved_companies.publisher, involved_companies.developer, name, platforms.name, summary"
const val ClientID = BuildConfig.ClientID
const val Auth = BuildConfig.Auth
interface ApiIGDB {

    @Headers(
        "Client-ID: $ClientID",
        "Authorization: $Auth",
        "Content-Type: text/plain")
    @POST("games")
    suspend fun getGameById(
        @Body requestBody: String
    ) : Response<List<GameSerialized>>

    @Headers(
            "Client-ID: $ClientID",
            "Authorization: $Auth",
            "Content-Type: application/json")
    @POST("games")
    suspend fun getGamesByName(
        @Query("search")  query: String,
        //id, age_ratings.rating, rating, cover.url, first_release_date, genres.name, involved_companies.company.name, name, platforms.name, summary
        @Query("fields") fields: String = fieldsString
        //@Query("fields") fields: String = "*"
        //@Query("fields") fields : String = "id,name"
 ) : Response<List<GameSerialized>>



}