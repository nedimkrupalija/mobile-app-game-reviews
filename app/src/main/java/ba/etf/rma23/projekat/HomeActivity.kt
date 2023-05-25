package ba.etf.rma23.projekat

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.core.os.ConfigurationCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.R
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameSerialized
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
            navController.navigate(R.id.action_homeItem_self)
            search("Counter")
            remove()
            /*val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://rma23ws.onrender.com/login"))
            startActivity(intent)*/
        }
        else{
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.detailsFragment) as NavHostFragment
            navHostFragment.navController.navigate(R.id.action_homeItem_to_gameDetailsItem)
        }



    }



     fun search(query: String){
        scope.launch{
            val result = GamesRepository.getGamesByName(query)

        }
    }
    fun login(){
        scope.launch {
            val result = AccountGamesRepository.getSavedGames()

        }
    }
    fun remove(){
        scope.launch {
            val result = AccountGamesRepository.removeGame(5)
        }
    }


}