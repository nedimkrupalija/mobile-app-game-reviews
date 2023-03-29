package ba.unsa.etf.rma.spirala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.spirala.GameData.Companion.getAll
import ba.unsa.etf.rma.spirala.GameData.Companion.getDetails

class HomeActivity : AppCompatActivity() {
    private lateinit var games: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var gamesList = getAll()
    private lateinit var homeButton: Button
    private lateinit var detailsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        games = findViewById(R.id.game_list)
        games.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        gameListAdapter = GameListAdapter(arrayListOf()) {game -> showDetails(game)}
        games.adapter = gameListAdapter
        gameListAdapter.updateGames(gamesList)

        homeButton = findViewById(R.id.home_button)
        detailsButton = findViewById(R.id.details_button)
        homeButton.isEnabled = false

        val extras = intent.extras
        detailsButton.isEnabled = extras != null

        detailsButton.setOnClickListener{
            if(extras!=null){
                val game = getDetails(extras.getString("game_title",""))
                showDetails(game!!)
            }
        }
    }
    private fun showDetails(game: Game){
        val intent = Intent(this, GameDetailsActivity::class.java).apply{
            putExtra("game_title",game.title)
        }
        startActivity(intent)
    }




}