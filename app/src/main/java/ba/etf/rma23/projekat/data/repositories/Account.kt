package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game

data class Account(
    val student: String,
    val acHash: String,
    val age: Int,
    val favoriteGames: List<Game>?


)
