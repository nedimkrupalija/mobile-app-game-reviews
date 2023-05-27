package ba.etf.rma23.projekat

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.GameData.Companion.getDetails
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    private lateinit var addButton: Button
    private lateinit var deleteButton: Button

    private lateinit var reviews: RecyclerView

    private lateinit var reviewsAdapter: GameReviewAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)
        title = view.findViewById(R.id.item_title_textview)
        platform = view.findViewById(R.id.platform_textview)
        coverImage = view.findViewById(R.id.cover_imageview)
        releaseDate = view.findViewById(R.id.release_date_textview)
        esrbRating = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)

        addButton = view.findViewById(R.id.insert_game_button)
        deleteButton = view.findViewById(R.id.delete_game_button)

        reviews = view.findViewById(R.id.impression_recyclerView)

        val orientation = resources.configuration.orientation

        val bundle: Bundle? = arguments
        if (bundle != null) {
            val gameString = bundle.getString("game")
            game = Gson().fromJson(gameString, Game::class.java)
            print(game.toString() + "\n")
           populateDetails()
        }
        else{
            game = getDetails("CS:GO")!!
            populateDetails()
        }
        
        reviews.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL, false
        )
        reviewsAdapter = GameReviewAdapter(listOf())
        reviews.adapter = reviewsAdapter
        var reviewsList: List<UserImpression>? = getDetails(game.title)?.userImpressions
        if (reviewsList != null) {
            reviewsAdapter.updateReview(reviewsList.sortedByDescending { it.timestamp })
        }


        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            val homeButton: BottomNavigationItemView = bottomNav.findViewById(R.id.homeItem)
            bottomNav.findViewById<BottomNavigationItemView>(R.id.gameDetailsItem).isEnabled = false


            homeButton.setOnClickListener {

                showHomeLayout()
            }
        }

        addButton.setOnClickListener {
            addGameToFavorites(game)
        }

        deleteButton.setOnClickListener {

        }


        return view
    }



    private fun addGameToFavorites(game: Game){
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        scope.launch {
            if(!AccountGamesRepository.saveGame(game)) {
                val toast = Toast.makeText(context, "Igrica već postoji u omiljenim", Toast.LENGTH_SHORT)
                toast.show()

            }
            else {
                val toast = Toast.makeText(
                    context,
                    "Uspješno ste dodali igru u omiljene",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
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
        Glide.with(context)
            .load("https://"+game.coverImage)
            .placeholder(R.drawable.steamicon)
            .error(id)
            .fallback(id)
            .into(coverImage)



    }
    private fun showHomeLayout(){
        val bundle = bundleOf("game_title" to game.title)
        requireView().findNavController().navigate(R.id.action_gameDetailsItem_to_homeItem, bundle)
    }

    private fun onSucces(games:List<Game>){
        val toast = Toast.makeText(context, "Added game to favorites", Toast.LENGTH_SHORT)
        toast.show()

    }
    private fun onError(){
        val toast = Toast.makeText(context, "Error while adding game to favorites", Toast.LENGTH_SHORT)
        toast.show()
    }

}