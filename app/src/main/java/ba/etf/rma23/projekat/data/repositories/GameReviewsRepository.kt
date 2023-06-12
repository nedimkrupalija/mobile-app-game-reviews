package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.GameReview

object GameReviewsRepository {
    fun getOfflineReviews() : List<GameReview>{
        return listOf()
    }
    fun sendOfflineReviews() : Int{
        return 0
    }

    fun sendReview() : Boolean{
        return true
    }
    fun getReviewsForGame(igdb_id: Int) : List<GameReview> {
        return listOf()
    }
}