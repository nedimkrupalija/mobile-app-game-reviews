package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.GameReview
import ba.etf.rma23.projekat.data.repositories.responses.AccountGameResponse
import ba.etf.rma23.projekat.data.repositories.responses.DeletedGameResponse
import ba.etf.rma23.projekat.data.repositories.responses.GameBodyResponse
import ba.etf.rma23.projekat.data.repositories.responses.SendGameReviewResponse
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


    @Headers("Content-Type: application/json")
    @POST("account/{aid}/game")
    suspend fun saveGame(
        @Path("aid") acHash: String,
        @Body ab : GameBodyResponse
    ) : Response<AccountGameResponse>

    @Headers("Content-Type: application/json")
    @GET("game/{gid}/gamereviews")
    suspend fun getGameReviews(
        @Path("gid") gameId: Int
    ) : Response<List<GameReview>>

    @Headers("Content-Type: application/json")
    @POST("account/{aid}/game/{gid}/gamereview")
    suspend fun createGameReview(
        @Path("aid") acHash: String,
        @Body body: GameReview,
        @Path("gid") gameId: Int
    ) : Response<GameReview>

    @Headers("Content-Type: application/json")
    @DELETE("account/{aid}/gamereviews")
    suspend fun deleteGameReviews(
        @Path("aid") acHash: String
    )

}