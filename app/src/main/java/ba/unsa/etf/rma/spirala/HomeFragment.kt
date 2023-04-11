package ba.unsa.etf.rma.spirala

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.spirala.GameData.Companion.getAll
import ba.unsa.etf.rma.spirala.GameData.Companion.getDetails
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        val bundle: Bundle? = arguments
        var game: Game?


        val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
        val detailsItem: BottomNavigationItemView = bottomNav.findViewById(R.id.gameDetailsItem)
        if(bundle?.getString("game_title") == null){
            detailsItem.isVisible = false
        }
        detailsItem.setOnClickListener{
            var game = getDetails(bundle!!.getString("game_title",""))
            showDetails(game)
        }
        return view
    }
    private fun showDetails(game: Game?){

        /* var arg = Bundle()
        arg.putString("game_title", game.title)


        val frag: Fragment = GameDetailsFragment()
        frag.arguments = arg
        fragmentManager?.commit {
            replace(R.id.gameDetailsItem, frag)
            setReorderingAllowed(true)
            addToBackStack(null)*/
        val bundle = bundleOf("game_title" to game?.title)
        requireView().findNavController().navigate(R.id.action_homeItem_to_gameDetailsItem, bundle)

    }


}