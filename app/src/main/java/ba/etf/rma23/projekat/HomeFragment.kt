package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.GameData.Companion.getAll
import ba.etf.rma23.projekat.GameData.Companion.getDetails
import ba.etf.rma23.projekat.R
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository.sortGames
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var games: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var gamesList : List<Game> = listOf()
    private lateinit var homeButton: Button
    private lateinit var detailsButton: Button
    private lateinit var searchButton: Button
    private lateinit var searchText: TextView
    private lateinit var sortButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        games = view.findViewById(R.id.game_list)
        games.layoutManager = LinearLayoutManager(container!!.context, LinearLayoutManager.VERTICAL, false)
        gameListAdapter = GameListAdapter(arrayListOf()) {game -> showDetails(game)}
        games.adapter = gameListAdapter
        //gameListAdapter.updateGames(gamesList)
        getGamesByName("")
        val orientation = resources.configuration.orientation
        searchButton = view.findViewById(R.id.search_button)
        searchText = view.findViewById(R.id.search_query_edittext)

        sortButton = view.findViewById(R.id.sort_button)

        val bundle: Bundle? = arguments
        var game: Game?

        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            val detailsItem: BottomNavigationItemView = bottomNav.findViewById(R.id.gameDetailsItem)
            bottomNav.findViewById<BottomNavigationItemView>(R.id.homeItem).isEnabled = false
            val search: String? = bundle?.getString("search_text")
            if(search != null){

            getGamesByName(search)
                searchText.text = search

        }
            if (bundle?.getString("game") == null) {
                detailsItem.isEnabled = false
            }
            detailsItem.setOnClickListener {
                val gameString = bundle?.getString("game")
                game = Gson().fromJson(gameString, Game::class.java)
                showDetails(game)
            }
        }
        searchButton.setOnClickListener {
            getGamesByName(searchText.text.toString())
        }

        sortButton.setOnClickListener {
            sortListedGames()
        }


        return view
    }

    private fun sortListedGames(){
        gamesList = sortGames()
        gameListAdapter.updateGames(gamesList)
        val toast = Toast.makeText(context, "Games sorted!", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showDetails(game: Game?){
        val gameString = Gson().toJson(game)
        val bundle = bundleOf("game" to gameString, "search_text" to searchText.text.toString())
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            requireView().findNavController()
                .navigate(R.id.action_homeItem_to_gameDetailsItem, bundle)
        }
        else{
            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.detailsFragment) as NavHostFragment
            navHostFragment.navController.navigate(R.id.action_gameDetailsItem_self, bundle)
        }

    }

    private fun getGamesByName(query: String){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val result = GamesRepository.getGamesByName(query)
            when(result){
                is List<Game> -> onSucces(result)
                else -> onError()
            }
        }
    }
    fun onSucces(games:List<Game>){
        val toast = Toast.makeText(context, "Found games", Toast.LENGTH_SHORT)
        toast.show()
        gameListAdapter.updateGames(games)
    }
    fun onError(){
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }


}