package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game

object  Account{
    val student: String = ""
    var acHash: String = ""
    var age: Int = 0
    var favoriteGames: List<Game> = listOf()



    fun gamesWithString(query: String) : List<Game>{
        val returnGames : MutableList<Game> = mutableListOf()
       for(game in favoriteGames){
           if(game.title.contains(query))
               returnGames.add(game)
       }
        return returnGames
    }



}

