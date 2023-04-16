package ba.unsa.etf.rma.spirala

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.unsa.etf.rma.spirala.GameData.Companion.getAll
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {

    fun hasItemCount(n: Int) = object : ViewAssertion {
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            Assert.assertTrue("View nije tipa RecyclerView", view is RecyclerView)
            var rv: RecyclerView = view as RecyclerView
            ViewMatchers.assertThat(
                "GetItemCount RecyclerView broj elementa: ",
                rv.adapter?.itemCount,
                CoreMatchers.`is`(n)
            )
        }
    }


    /**
     * Ovim testom se testira da li aplikacija ispravno funkcionise u portrait modu
     * i nakon ubacivanja fragmenata. Testira se i ispravno funkcionisanje navigacione komponente,
     * odnosno da li je moguce otici na detalje igre i preko klika na igru i preko navigacije.
     * Usput se testira i raspored u GameDetailsFragmentu (inace je bez znacajnih izmjena, samo sto je
     * u landscape modu stavljen u scroll view)
     *
     * U ovom testu provjerava se da li je details dugme iskljuceno u pocetku, zatim se simulira klik
     * na Call of Duty gdje se provjerava da li je to zaista ta igrica. Nakon toga se vraća na home
     * i opet na details gdje se opet provjeri da je ispravan fragment pokrenut.
     *
     * Osiguravano je da test testira navedeno preko metoda Espressa kao sto su perform(), click()
     * check(), matches() i druge.
     *
     */
    @Test
    fun portraitTest() {
        launchActivity<HomeActivity>()
        val game = getAll().get(1)
        onView(withId(R.id.gameDetailsItem)).check(matches(not(isEnabled())))

        onView(withId(R.id.game_list))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)).check(
                matches(
                    allOf(
                        hasDescendant(withId(R.id.item_title_textview)),
                        hasDescendant(withId(R.id.game_rating_textview)),
                        hasDescendant(withId(R.id.release_date)),
                        hasDescendant(withId(R.id.game_platform_textview)),
                        hasDescendant(withId(R.id.game_rating_textview)),

                    )
                )
            ).check(hasItemCount(10)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(
                hasDescendant(withText(game.title)),
                hasDescendant(withText(game.releaseDate)),
                hasDescendant(withText(game.rating.toString()))
            ),click()))
        onView(withId(R.id.esrb_rating_textview)).check(matches(withText("ESRB Rating: Mature")))
        onView(withId(R.id.publisher_textview)).check(matches(withText(containsString("Activision"))))

        onView(withId(R.id.item_title_textview)).check(
            isCompletelyAbove(
            withId(R.id.esrb_rating_textview)
        ))
        onView(withId(R.id.developer_textview)).check(isCompletelyAbove(withId(R.id.genre_textview)))
        onView(withId(R.id.description_textview)).check(isCompletelyAbove(withId(R.id.impression_recyclerView)))

        onView(withId(R.id.impression_recyclerView)).check(hasItemCount(5))

        onView(withId(R.id.homeItem)).perform(click())

        onView(withId(R.id.gameDetailsItem)).check(matches(isEnabled()))
        onView(withId(R.id.gameDetailsItem)).perform(click())

        onView(withId(R.id.esrb_rating_textview)).check(matches(withText("ESRB Rating: Mature")))
        onView(withId(R.id.publisher_textview)).check(matches(withText(containsString("Activision"))))
    }
}