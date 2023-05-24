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
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var games: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var gamesList = getAll()
    private lateinit var homeButton: Button
    private lateinit var detailsButton: Button
    private lateinit var searchButton: Button
    private lateinit var searchText: TextView

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
        gameListAdapter.updateGames(gamesList)

        val orientation = resources.configuration.orientation
        searchButton = view.findViewById(R.id.search_button)
        searchText = view.findViewById(R.id.search_query_edittext)

        val bundle: Bundle? = arguments
        var game: Game?

        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            val detailsItem: BottomNavigationItemView = bottomNav.findViewById(R.id.gameDetailsItem)
            bottomNav.findViewById<BottomNavigationItemView>(R.id.homeItem).isEnabled = false

            if (bundle?.getString("game_title") == null) {
                detailsItem.isEnabled = false
            }
            detailsItem.setOnClickListener {
                var game = getDetails(bundle!!.getString("game_title", ""))
                showDetails(game)
            }
        }
        searchButton.setOnClickListener {
            getGamesByName(searchText.text.toString())
        }


        return view
    }
    private fun showDetails(game: Game?){

        val bundle = bundleOf("game_title" to game?.title)
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