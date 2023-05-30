package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.data.repositories.responses.AccountGameResponse
import ba.etf.rma23.projekat.data.repositories.responses.DeletedGameResponse
import ba.etf.rma23.projekat.data.repositories.responses.GameBodyResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiAccount {
    @Headers("Content-Type: application/json")
    @GET("login")
    suspend fun login() : Response<String>

    @Headers("Content-Type: application/json")
    @GET("account/{aid}/games")
    suspend fun getUserGames(@Path("aid") acHash: String):Response<List<AccountGameResponse>>

    @Headers("Content-Type: application/json")
    @DELETE("account/{aid}/game/{gid}")
    suspend fun removeGame(
        @Path("aid") acHash: String,
        @Path("gid") gameId: Int
    ) : Response<DeletedGameResponse>

    /**
     * Popraviti ne radi
     */
    @Headers("Content-Type: application/json")
    @POST("account/{aid}/game")
    suspend fun saveGame(
        @Path("aid") acHash: String,
        @Body ab : GameBodyResponse
    ) : Response<AccountGameResponse>

}