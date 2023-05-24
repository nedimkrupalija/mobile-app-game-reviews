package ba.etf.rma23.projekat.data.repositories

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

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
        @Path("aid") acHash: String ,
        @Path("gid") gameId: Int
    ) : Response<DeletedGameResponse>

}