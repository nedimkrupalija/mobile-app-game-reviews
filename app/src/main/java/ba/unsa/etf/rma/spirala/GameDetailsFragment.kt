package ba.unsa.etf.rma.spirala

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameDetailsFragment : Fragment() {
    private lateinit var game: Game
    private lateinit var title: TextView
    private lateinit var platform: TextView
    private lateinit var coverImage: ImageView
    private lateinit var releaseDate: TextView
    private lateinit var esrbRating: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView

    private lateinit var homeButton: Button
    private lateinit var detailsButton: Button

    private lateinit var reviews: RecyclerView

    private lateinit var reviewsAdapter: GameReviewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)
        title = view.findViewById(R.id.game_title_textview)
        platform = view.findViewById(R.id.platform_textview)
        coverImage = view.findViewById(R.id.cover_imageview)
        releaseDate = view.findViewById(R.id.release_date_textview)
        esrbRating = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)
        homeButton = view.findViewById(R.id.home_button)
        detailsButton = view.findViewById(R.id.details_button)
        detailsButton.isEnabled = false
        reviews = view.findViewById(R.id.impression_recyclerView)
        val extras = activity?.intent?.extras
        if (extras != null) {
            game = GameData.getDetails(extras.getString("game_title", ""))!!
            populateDetails()
        }
        reviews.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL, false
        )
        reviewsAdapter = GameReviewAdapter(listOf())
        reviews.adapter = reviewsAdapter
        var reviewsList: List<UserImpression>? = GameData.getDetails(game.title)?.userImpressions
        if (reviewsList != null) {
            reviewsAdapter.updateReview(reviewsList.sortedByDescending { it.timestamp })
        }
        homeButton.setOnClickListener{
            showHomeLayout()
        }
        return view
    }
    private fun populateDetails(){

        title.text = game.title
        platform.text = "Platform: " + game.platform
        releaseDate.text = "Release date: " + game.releaseDate
        esrbRating.text = "ESRB Rating: " + game.esrbRating
        developer.text = "Developer: " + game.developer
        publisher.text = "Publisher: " + game.publisher
        genre.text = "Genre: " + game.genre
        description.text = game.description

        val context: Context = coverImage.context
        var id: Int = context.resources.getIdentifier(game.coverImage,"drawable",context.packageName)
        if (id == 0) id = context.resources.getIdentifier("steamicon","drawable", context.packageName)
        coverImage.setImageResource(id)

    }
    private fun showHomeLayout(){
        val intent = Intent(activity, HomeActivity::class.java).apply {
            putExtra("game_title",game.title)
        }
        startActivity(intent)
    }
}