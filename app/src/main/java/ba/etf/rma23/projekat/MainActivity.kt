package ba.etf.rma23.projekat

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.AppDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.net.ConnectException

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Job() + Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
         setLocalGames()
        print("PROSAO SET LOCAL GAMES \n")
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
            navController.navigate(R.id.action_homeItem_self)

        }
        else{
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.detailsFragment) as NavHostFragment
            navHostFragment.navController.navigate(R.id.action_homeItem_to_gameDetailsItem)
        }
       /* scope.launch {
            var db = AppDatabase.getInstance(applicationContext)

        db.gameReviewDao().insert(GameReview(2,"a", 125, false, "", ""))
        } */


    }

     fun setLocalGames(){
        scope.launch{
            try {
                AccountGamesRepository.Account.favoriteGames =
                    AccountGamesRepository.getSavedGames() as MutableList<Game>
            }
            catch(ex: Exception)
            {
                print("MAIN EXCP: \n" )
                AccountGamesRepository.Account.favoriteGames = mutableListOf()
            }
            catch(ex: ConnectException)
            {
                print("CONNECT EXCP!\n")
                AccountGamesRepository.Account.favoriteGames = mutableListOf()
            }
        }
    }





}