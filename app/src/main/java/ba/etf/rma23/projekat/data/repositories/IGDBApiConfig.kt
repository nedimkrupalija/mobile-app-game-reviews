package ba.etf.rma23.projekat.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object IGDBApiConfig {
    val retrofit : ApiIGDB  = Retrofit.Builder()
        .baseUrl("https://api.igdb.com/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiIGDB::class.java)
}