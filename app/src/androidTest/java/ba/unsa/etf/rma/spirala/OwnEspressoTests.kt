package ba.unsa.etf.rma.spirala

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.unsa.etf.rma.spirala.GameData.Companion.getAll
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {

    /**
     * Custom matcher kopiran iz prethodne spirale koji testira da li RecyclerView ima n elemenata
     */
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
    @get:Rule
     public val rule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

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

        val game = getAll().get(1)
        onView(withId(R.id.gameDetailsItem)).check(matches(not(isEnabled())))

        onView(withId(R.id.game_list))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1)).check(
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
        rule.scenario.close()
    }

    /**
     * Custom matcher koji je bio potreban zato sto je izbacivalo gresku da se nalazi vise view-ova
     * sa istim nazivom (ID-om) u hijerarhiji. Npr homeFragment i detailsFragment. Pa ovaj matcher
     * uzima view sa zadatim indexom.
     */
    fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?>? {
        return object : TypeSafeMatcher<View>() {
            var currentIndex = 0
            var viewObjHash = 0

            @SuppressLint("DefaultLocale")
            override fun describeTo(description: Description) {
                description.appendText(String.format("with index: %d ", index))
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                if (matcher.matches(view) && currentIndex++ == index) {
                    viewObjHash = view.hashCode()
                }
                return view.hashCode() == viewObjHash
            }
        }
    }


    /**
     * Test koji ispituje landscape mode. Prvo se telefon okrene u landscape mode, a zatim se provjeri
     * da li su fragmenti home i details prikazani. Provjeri se da li je upaljena prva igrica (CS:GO) na
     * details ekranu, a zatim da li na home fragmentu ima 10 igrica. Zatim se provjerava da li jos radi
     * klik na neku igricu (u ovom slucaju World of Tanks) te da li na details fragmentu je zaista
     * ta igra prikazana.
     */
    @Test
    fun landscapeTest(){
        rule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        val game = getAll().get(5)
        // Test da li je details ispravno otvoren na prvu igricu (CS:GO)
       onView(withIndex(withId(R.id.homeFragment),0)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.detailsFragment),0)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.detailsFragment),0)).check(matches(allOf(
            hasDescendant(withText("CS:GO")),
            hasDescendant(withSubstring("Valve")),
            hasDescendant(withSubstring("was released")),
            hasDescendant(withSubstring("Mature")),
            hasDescendant(withSubstring("Steam")),
            hasDescendant(withId(R.id.impression_recyclerView))
        )))
        onView(withId(R.id.impression_recyclerView)).check((hasItemCount(5)))

        // Test da li je home fragment ispravno otvoren sa 10 igara i klik na World of Tanks
        onView(withIndex(withId(R.id.game_list),0)).check(hasItemCount(10))
        onView(withId(R.id.game_list))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5)).check(
                matches(
                    allOf(
                        hasDescendant(withId(R.id.item_title_textview)),
                        hasDescendant(withId(R.id.game_rating_textview)),
                        hasDescendant(withId(R.id.release_date)),
                        hasDescendant(withId(R.id.game_platform_textview)),
                        hasDescendant(withId(R.id.game_rating_textview)),

                        )
                )).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(
            hasDescendant(withText(game.title)),
            hasDescendant(withText(game.releaseDate)),
            hasDescendant(withText(game.rating.toString()))
        ),click()))

        //Provjera da li je to zaista world of tanks
        onView(withIndex(withId(R.id.detailsFragment),0)).check(matches(allOf(
            hasDescendant(withText("World of Tanks")),
            hasDescendant(withSubstring("Wargaming")),
            hasDescendant(withSubstring("The player takes")),
            hasDescendant(withSubstring("Teen"))
        )))


        rule.scenario.close()
    }


    /**
     * Ovaj test je napravljen uglavnom zato sto se prije desavalo da aplikacija crasha kada se iz
     * details fragmenta prebaci u landscape i vrati nazad. Upravo se to testira, prvo se otvori
     * Among Us pa se okrene orijentacija i tu se testira da li se jos moze sve ispravno izabrati neka
     * igrica u home fragmentu. Nakon toga se vraca orijentacija u portrait i testira se da li jos
     * sve ispravno funkcionise (biranje igrice i navbar).
     */
    @Test
    fun landscapeTest2(){


        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(
            hasDescendant(withText("Among Us"))
        ),click()))

        rule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
            allOf(
                hasDescendant(withSubstring("Most Wanted"))
            ), click()))

        onView(withIndex(withId(R.id.item_title_textview),0)).check(matches(withText("Need For Speed: Most Wanted")))

        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
            allOf(
                hasDescendant(withSubstring("League of Legends"))
            ), click()))
        onView(withIndex(withId(R.id.item_title_textview),0)).check(matches(withText("League of Legends")))

        rule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))
        onView(withId(R.id.homeItem)).check(matches(isDisplayed()))

        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
            allOf(
                hasDescendant(withSubstring("Fortnite"))
            ), click()))
        onView(withIndex(withId(R.id.item_title_textview),0)).check(matches(withText("Fortnite")))

        onView(withId(R.id.homeItem)).perform(click())
        onView(withId(R.id.game_list)).check(matches(
            isDisplayed()
        )).check(hasItemCount(10))

        onView(withId(R.id.gameDetailsItem)).perform(click())
        onView(withId(R.id.item_title_textview)).check(matches(withText("Fortnite")))
        rule.scenario.close()
    }

}