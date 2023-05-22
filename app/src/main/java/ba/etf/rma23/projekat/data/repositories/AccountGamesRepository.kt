package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game

class AccountGamesRepository {
    fun setHash(acHash: String): Boolean{
        return true
    }
    fun getHash(): String{
        return "a"
    }
    fun getSavedGames():List<Game>{
        return listOf()
    }
    // Promijeniti upitnik
    fun saveGame() : Game?{
        return null
    }
    fun removeGame(id: Int): Boolean{
        return true
    }
    fun removeNonSafe():Boolean{
        return true
    }
    fun getGamesContainingString(query:String):List<Game>{
        return listOf()
    }
    fun setAge(age:Int):Boolean{
        return true
    }


}