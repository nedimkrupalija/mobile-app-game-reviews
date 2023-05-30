package ba.etf.rma23.projekat

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Job() + Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setLocalGames()
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
            navController.navigate(R.id.action_homeItem_self)
           // search("Counter")
            //remove()
            /*val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://rma23ws.onrender.com/login"))
            startActivity(intent)*/
            //remove()
           // print(search("abc").toString() + "\n")
          //  search("abc")

        }
        else{
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.detailsFragment) as NavHostFragment
            navHostFragment.navController.navigate(R.id.action_homeItem_to_gameDetailsItem)
        }



    }

     fun setLocalGames(){
        scope.launch{
            AccountGamesRepository.Account.favoriteGames = AccountGamesRepository.getSavedGames() as MutableList<Game>
            print("MAIN OMILJENE: " + AccountGamesRepository.Account.favoriteGames.toString() + "\n")
        }
    }

}