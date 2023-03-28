package ba.unsa.etf.rma.spirala

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.spirala.GameData.Companion.getAll
import ba.unsa.etf.rma.spirala.GameData.Companion.getDetails

class GameDetailsActivity : AppCompatActivity() {
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

    private lateinit var reviews: RecyclerView

    private lateinit var reviewsAdapter: GameReviewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        title = findViewById(R.id.game_title_textview)
        platform = findViewById(R.id.game_platform_textview)
        coverImage = findViewById(R.id.cover_imageview)
        releaseDate = findViewById(R.id.release_date_textview)
        esrbRating = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre = findViewById(R.id.genre_textview)
        description = findViewById(R.id.description_textview)

        reviews = findViewById(R.id.impression_recyclerView)
        val extras = intent.extras
        if (extras != null) {
            game = getDetails(extras.getString("game_title", ""))!!
            populateDetails()
        }
        reviews.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        reviewsAdapter = GameReviewAdapter(listOf())
        reviews.adapter = reviewsAdapter
        var reviewsList: List<UserImpression>? = getDetails(game.title)?.userImpressions
        if (reviewsList != null) {
        reviewsAdapter.updateReview(reviewsList.sortedByDescending { it.timestamp })
     }

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


}