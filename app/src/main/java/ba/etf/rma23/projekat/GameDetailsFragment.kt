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
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.*

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


    private lateinit var reviews: RecyclerView
    private var reviewsList : MutableList<UserImpression> = mutableListOf()
    private lateinit var reviewsAdapter: GameReviewAdapter
    var searchText : String = ""
    private var isPresentLocal : Boolean = false
    private var reviewResponse : List<GameReview> = listOf()


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



        reviews = view.findViewById(R.id.impression_recyclerView)

        val orientation = resources.configuration.orientation
        game = Game(0, "", "" , "", 5.0, "", "", "", "", "", "", listOf())

        val bundle: Bundle? = arguments
        if (bundle != null) {
            val gameString = bundle.getString("game")
            game = Gson().fromJson(gameString, Game::class.java)
            searchText = bundle.getString("search_text").toString()
            getReviewsById(game.id)
           populateDetails()
        }
        else{
            getById(241)
            getReviewsById(241)

        }
        
        reviews.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL, false
        )
        reviewsAdapter = GameReviewAdapter(listOf())
        reviews.adapter = reviewsAdapter
         //getDetails(game.title)?.userImpressions








        reviewsAdapter.updateReview(reviewsList.sortedByDescending { it.timestamp })



        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            val homeButton: BottomNavigationItemView = bottomNav.findViewById(R.id.homeItem)
            bottomNav.findViewById<BottomNavigationItemView>(R.id.gameDetailsItem).isEnabled = false


            homeButton.setOnClickListener {

                showHomeLayout()
            }
        }
        isPresentLocal = AccountGamesRepository.Account.isInLocalGames(game.id)
        if(isPresentLocal){
            addButton.text = "Remove from favorites"
        }
        else{
            addButton.text = "Add to favorites"
        }

        addButton.setOnClickListener {
            addGameToFavorites(game)
        }




        return view
    }



    private fun getById(id : Int){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            game = GamesRepository.getGameById(id)
            populateDetails()
        }
    }



    private fun getReviewsById(id: Int){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val result = GameReviewsRepository.getReviewsForGame(id)
            print("RESULT: " + result.toString() + "\n")
            for(review in result){
                if(review.review != null){
                    reviewsList.add(UserReview(review.student,review.timestamp.toLong(),review.review!!))

                }
                if(review.rating != null){
                    reviewsList.add(UserRating(review.student, review.timestamp.toLong(),review.rating!!.toDouble()))
                }
            }
            reviewsAdapter.updateReview(reviewsList)
        }
    }



    private fun addGameToFavorites(game: Game){
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        isPresentLocal = AccountGamesRepository.Account.isInLocalGames(game.id)
        scope.launch {
            if(!isPresentLocal){
                AccountGamesRepository.saveGame(game)
            }
            else {
                AccountGamesRepository.removeGame(game.id)
            }
        }
        if(isPresentLocal){
            val toast = Toast.makeText(context, "Game succesfully deleted from favorites!", Toast.LENGTH_SHORT)
            toast.show()
            addButton.text = "Add to favorites"
        }
        else{
            val toast = Toast.makeText(
                context,
                "Game succesfully added to favorites!",
                Toast.LENGTH_SHORT
            )
            toast.show()
            addButton.text = "Remove from favorites"
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
        val gameString = Gson().toJson(game)
        val bundle = bundleOf("search_text" to searchText, "game" to gameString)
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


