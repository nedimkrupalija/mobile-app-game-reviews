package ba.unsa.etf.rma.spirala

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class GameListAdapter(
    private var games: List<Game>
): RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.gameTitle.text = games[position].title
        holder.gamePlatform.text = games[position].platform
        holder.game_rating.text = games[position].rating.toString()
        holder.gameReleaseDate.text = games[position].releaseDate

    }

    fun updateGames(games: List<Game>){
        this.games = games
        notifyDataSetChanged()
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val gameTitle: TextView = itemView.findViewById(R.id.game_title_textview)
        val game_rating: TextView = itemView.findViewById(R.id.game_rating_textview)
        val gameReleaseDate: TextView = itemView.findViewById(R.id.game_release_date_textview)
        val gamePlatform: TextView = itemView.findViewById(R.id.game_platform_textview)

    }
}