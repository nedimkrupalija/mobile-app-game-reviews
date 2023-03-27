package ba.unsa.etf.rma.spirala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.spirala.GameData.Companion.getAll

class HomeActivity : AppCompatActivity() {
    private lateinit var games: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var gamesList = getAll()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        games = findViewById(R.id.game_list)
        games.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        gameListAdapter = GameListAdapter(listOf())
        games.adapter = gameListAdapter
        gameListAdapter.updateGames(gamesList)
    }
}