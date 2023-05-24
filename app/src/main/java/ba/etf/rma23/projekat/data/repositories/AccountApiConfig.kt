package ba.etf.rma23.projekat.data.repositories

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object AccountApiConfig {
    val gson: Gson = GsonBuilder().setLenient().create()
    val retrofit : ApiAccount  = Retrofit.Builder()
        .baseUrl("https://rma23ws.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiAccount::class.java)
}