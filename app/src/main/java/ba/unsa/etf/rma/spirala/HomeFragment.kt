package ba.unsa.etf.rma.spirala

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.navigation.findNavController
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
        Log.d("POZVAN HOMEFRAGMENT", "HOMEFRAGMENT")

        return view
    }
    private fun showDetails(game: Game){

        /* var arg = Bundle()
        arg.putString("game_title", game.title)


        val frag: Fragment = GameDetailsFragment()
        frag.arguments = arg
        fragmentManager?.commit {
            replace(R.id.gameDetailsItem, frag)
            setReorderingAllowed(true)
            addToBackStack(null)*/
        val bundle = bundleOf("game_title" to game.title)
        requireView().findNavController().navigate(R.id.action_homeItem_to_gameDetailsItem, bundle)
        }


}