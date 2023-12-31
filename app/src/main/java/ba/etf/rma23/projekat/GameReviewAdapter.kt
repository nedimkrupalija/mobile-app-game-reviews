package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class GameReviewAdapter(private var reviews: List<UserImpression>):
RecyclerView.Adapter<GameReviewAdapter.GameDetailViewHolder>(){

    inner class GameDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username_textview)
        val review: TextView = itemView.findViewById(R.id.review_textview)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_review_layout,parent,false)
        return GameDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: GameDetailViewHolder, position: Int) {
        holder.username.text = reviews[position].userName
        if (reviews[position] is UserReview){
            val review: String = (reviews[position] as UserReview).review.toString()
            holder.review.text =  review
            holder.ratingBar.isVisible = false
        }
        else if(reviews[position] is UserRating){
            val rating: Double = (reviews[position] as UserRating).rating
            holder.ratingBar.rating = rating.toFloat()
            holder.review.isVisible = false
        }
    }
    fun updateReview(reviews: List<UserImpression>?){
        if (reviews != null) {
            this.reviews = reviews
        }
        notifyDataSetChanged()
    }
}