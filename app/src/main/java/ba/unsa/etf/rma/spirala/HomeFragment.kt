package ba.unsa.etf.rma.spirala

import android.app.Application
import android.content.Intent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.spirala.GameData.Companion.getAll

class HomeFragment : Fragment() {
    private lateinit var games: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var gamesList = getAll()
    private lateinit var homeButton: Button
    private lateinit var detailsButton: Button

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
        return view
    }
    private fun showDetails(game: Game){
        val intent = Intent(activity, GameDetailsActivity::class.java).apply{
            putExtra("game_title",game.title)
        }
        startActivity(intent)
    }
}